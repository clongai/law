package com.law.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.law.annotation.Component;
import com.law.common.Constant;
import com.law.common.OrderStatusEnum;
import com.law.common.TableStatusEnum;
import com.law.entity.LawFileRecord;
import com.law.entity.LawOrderExt;
import com.law.idworker.IdWorker;
import com.law.model.Chat;
import com.law.model.KeyValue;
import com.law.model.Law;
import com.law.model.LawCaseDropdownModel;
import com.law.model.LawOrder;
import com.law.model.LawPay;
import com.law.model.LawPerson;
import com.law.model.LawPromoter;
import com.law.model.LawServiceEntity;
import com.law.model.Message;
import com.law.model.OrderPO;
import com.law.model.User;
import com.law.service.LawFileRecordService;
import com.law.service.LawOrderExtService;
import com.law.service.LawPayService;
import com.law.service.LawPersonService;
import com.law.service.LawPromoterService;
import com.law.service.LawService;
import com.law.util.FileUtil;
import com.law.util.ListUtil;

@RestController
public class LawController {

	private static final Logger log = LoggerFactory.getLogger(LawController.class);

	@Value("${law.upload.dir}")
	private String uploadDir;

	@Autowired
	IdWorker idWorker;

	@Autowired
	LawService lawService;
	@Autowired
	LawPromoterService lawPromoterService;

	@Autowired
	private LawOrderExtService lawOrderExtService;

	@Autowired
	private LawPersonService lawPersonService;

	@Autowired
	private LawFileRecordService lawFileRecordService;

	@Autowired
	private LawPayService lawPayService;

	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			String fileName = file.getOriginalFilename();
			if (!StringUtils.isBlank(fileName)) {

				String outFileName = idWorker.nextId() + "." + FileUtil.getFileType(fileName);
				File outFile = new File(uploadDir + outFileName);
				FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
				return "https://law.loadpeople.com/files/" + outFileName;
			}
		} catch (Exception e) {
			log.error("文件上传失败：", e);
		}
		return "";
	}

	@PostMapping("/uploadFileOrderId")
	public String uploadFileOrderId(@RequestParam("file") MultipartFile file, @RequestParam("orderId") Integer orderId) {
		try {
			String fileName = file.getOriginalFilename();
			if (!StringUtils.isBlank(fileName)) {

				String outFileName = idWorker.nextId() + "." + FileUtil.getFileType(fileName);
				File outFile = new File(uploadDir + outFileName);
				FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
				if (orderId != null) {
					LawFileRecord lawFile = new LawFileRecord();
					lawFile.setCreateDate(new Date());
					lawFile.setFileName(outFileName);
					lawFile.setFileOrgName(fileName);
					lawFile.setFilePath(outFile.getPath());
					lawFile.setOrderId(orderId);
					lawFileRecordService.save(lawFile);
				}
				return "https://law.loadpeople.com/files/" + outFileName;
			}
		} catch (Exception e) {
			log.error("文件上传失败：", e);
		}
		return "";
	}

	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletResponse response, @RequestParam("fileId") Integer fileId) {
		if (fileId != null) {
			//lawFileRecordService.findAllByOrderId(orderId);
			LawFileRecord lawFile = lawFileRecordService.findOneById(fileId);
			File file = new File(lawFile.getFilePath());

			//设置文件路径
			// 如果文件名存在，则进行下载
			if (file.exists()) {

				// 配置文件下载
				response.setHeader("content-type", "application/octet-stream");
				response.setContentType("application/octet-stream");
				// 下载文件能正常显示中文
				try {
					response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}

				// 实现文件下载
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					System.out.println("Download the song successfully!");
				} catch (Exception e) {
					System.out.println("Download the song failed!");
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}
	}

	@RequestMapping("/download")
	public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		// 获取指定目录下的第一个文件
		File scFileDir = new File("E://music_eg");
		File TrxFiles[] = scFileDir.listFiles();
		System.out.println(TrxFiles[0]);
		String fileName = TrxFiles[0].getName(); //下载的文件名

		// 如果文件名不为空，则进行下载
		if (fileName != null) {
			//设置文件路径
			String realPath = "E://music_eg/";
			File file = new File(realPath, fileName);

			// 如果文件名存在，则进行下载
			if (file.exists()) {

				// 配置文件下载
				response.setHeader("content-type", "application/octet-stream");
				response.setContentType("application/octet-stream");
				// 下载文件能正常显示中文
				response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

				// 实现文件下载
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					System.out.println("Download the song successfully!");
				} catch (Exception e) {
					System.out.println("Download the song failed!");
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}

	@RequestMapping("/addLawOrder")
	public @ResponseBody LawOrder addLawOrder(@RequestBody Law law) {
		return lawService.addLawOrder(law);
	}

	@RequestMapping("/queryLawOrderByOpenId")
	public List<LawOrder> queryLawOrder(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		String openId = json.getString("openId");
		return lawService.queryLawOrderByOpenId(openId);
	}

	@RequestMapping("/countLawOrderByStatus")
	public Integer countLawOrderByStatus(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		String status = json.getString("status");
		return lawService.countLawOrderByStatus(status);
	}

	@RequestMapping("/queryLawOrderByStatus")
	@Component("table")
	public PageImpl<OrderPO> queryLawOrderByStatus(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		String status = json.getString("status");
		String serviceLevel = json.getString("serviceLevel");
		int page = json.getIntValue("page");
		int pageSize = json.getIntValue("pageSize");
		Page<LawOrder> returnList = lawService.queryLawOrderByStatus(status, serviceLevel, page, pageSize);
		if (returnList != null) {
			if (returnList.getContent() != null) {
				@SuppressWarnings("deprecation")
				Pageable pageable = new PageRequest(page, pageSize);
				List<OrderPO> list = new ArrayList<>();
				returnList.getContent().forEach(t -> {
					OrderPO op = new OrderPO();
					BeanUtils.copyProperties(t, op);
					op.setStatusVal(t.getStatus());
					list.add(op);
				});
				PageImpl<OrderPO> pageImpl = new PageImpl<OrderPO>(list, pageable, returnList.getTotalElements());
				return pageImpl;
			}
		}
		return null;
	}

	@RequestMapping("/getLawOrder")
	public Law getLawOrder(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		Integer orderId = json.getInteger("orderId");
		Integer orginazation = json.getInteger("orginazation");
		return lawService.queryLaw(orderId, orginazation);
	}

	@RequestMapping("/accpetLawOrder")
	public LawOrder accpetLawOrder(@RequestBody String data) {
		return lawService.accpetLawOrder(data);
	}

	@RequestMapping("/refuseLawOrder")
	public LawOrder refuseLawOrder(@RequestBody String data) {
		return lawService.refuseLawOrder(data);
	}

	@RequestMapping("/passLawOrder")
	public LawOrder passLawOrder(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		Integer orderId = json.getInteger("orderId");
		LawOrder order = lawService.queryLawOrder(orderId);
		lawService.move2LawOrderHis(order);
		order.setDoneCode(idWorker.nextId());
		order.setDoneDate(new Timestamp(System.currentTimeMillis()));
		order.setStatus("7");//转交专家团队
		return lawService.saveLawOrder(order);
	}

	@RequestMapping("/queryLawService")
	public List<LawServiceEntity> queryLawService(@RequestBody String data) {
		return lawService.queryLawService();
	}

	@RequestMapping("/addPayOrder")
	public LawPay addPayOrder(@RequestBody String data) throws Exception {
		JSONObject json = JSONObject.parseObject(data);
		LawPay lawPay = json.getObject("lawPay", LawPay.class);
		String status = json.getString("status");
		Integer serviceId = json.getInteger("serviceId");
		int orderId = lawPay.getOrderId();
		//如果是初审通过付款，保存咨询档次到报单中
		LawOrder lawOrder = lawService.queryLawOrder(orderId);
		lawService.move2LawOrderHis(lawOrder);
		lawOrder.setDoneCode(idWorker.nextId());
		lawOrder.setDoneDate(new Timestamp(System.currentTimeMillis()));
		if (status.equalsIgnoreCase("1")) {
			lawOrder.setServiceId(serviceId);
			lawOrder.setStatus("2");//状态改为初审通过已支付
		} else if (status.equalsIgnoreCase("4")) {
			lawOrder.setStatus("5");//状态改为二审通过已支付
		} else if (status.equalsIgnoreCase("8")) {
			lawOrder.setStatus("9");//状态改为补充二审通过已付款
		}
		lawService.saveLawOrder(lawOrder);

		lawPay.setDoneCode(idWorker.nextId());
		lawPay.setDoneDate(new Timestamp(System.currentTimeMillis()));
		lawService.addPayOrder(lawPay);

		return lawPay;
	}

	@RequestMapping("/findCaseByName")
	public List<LawCaseDropdownModel> findCaseByName(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		String caseName = json.getString("caseName");
		String caseType = json.getString("caseType");
		String parentCase = json.getString("parentCase");
		return lawService.findCaseByName(caseName, caseType, parentCase);
	}

	@RequestMapping("/findAllBaseCase")
	public List<LawCaseDropdownModel> findAllBaseCase() {
		return lawService.findAllBaseCase();
	}

	@RequestMapping("/getRelationData")
	public Map<String, Object> getRelationData(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		String openId = json.getString("openId");
		//获取个人信息
		return lawPromoterService.getRelationData(openId);
	}

	@RequestMapping("/getLawOrderExt")
	public LawOrderExt getLawOrderExt(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		Integer orderId = json.getInteger("orderId");
		return lawOrderExtService.findByOrderId(orderId);
	}

	@RequestMapping("/uodateOrderExt")
	public LawOrderExt uodateOrderExt(@RequestBody String data) {
		return lawOrderExtService.uodateOrderExt(data);
	}

	@RequestMapping("/getPayedPageInitData")
	public OrderPO getPayedPageInitData(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		Integer orderId = json.getInteger("orderId");
		OrderPO orderPo = new OrderPO();
		LawOrder lawOrder = lawService.queryLawOrder(orderId);
		BeanUtils.copyProperties(lawOrder, orderPo);
		// 获取受理时间，材料清单，联系人，联系电话
		LawOrderExt lawOrderExt = lawOrderExtService.findByOrderId(orderId);
		if (lawOrderExt != null) {
			JSONObject parseObject = JSONObject.parseObject(JSONObject.toJSONString(lawOrderExt));
			if (lawOrderExt.getFaceSelected() != null) {
				Date acceptStartTime = parseObject.getDate("acceptStartTime" + (lawOrderExt.getFaceSelected() + 1));
				Date acceptEndTime = parseObject.getDate("acceptEndTime" + (lawOrderExt.getFaceSelected() + 1));
				orderPo.setAcceptStartTime(acceptStartTime);
				orderPo.setAcceptEndTime(acceptEndTime);
			}
			if (lawOrderExt.getFileList() != null) {
				orderPo.setFileList(lawOrderExt.getFileList().replaceAll("\n", ""));
			}

		}
		//获取案件当事人信息
		List<LawPerson> partyPerson = lawPersonService.findAllByOrderIdAndPersonTypeOrderByPersonId(orderId, "1");
		if (partyPerson != null && !partyPerson.isEmpty()) {
			String personName = partyPerson.get(0).getPersonName();
			orderPo.setPartyPersonName(personName);
		}
		List<LawFileRecord> lfrList = lawFileRecordService.findAllByOrderId(orderId);
		orderPo.setLawFileList(lfrList);
		return orderPo;
	}

	@RequestMapping("/customeRefuseOrder")
	public LawOrder customeRefuseOrder(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		Integer orderId = json.getInteger("orderId");
		String refuseReason = json.getString("refuseReason");
		LawOrder lawOrder = lawService.queryLawOrder(orderId);
		if (lawOrder != null) {
			lawOrder.setRefuseReason(refuseReason);
			lawOrder.setStatus(OrderStatusEnum.CUSTOMER_END.getCode());
			return lawService.saveLawOrder(lawOrder);
		}
		return null;
	}

	@RequestMapping("/getFileByOrderId")
	public List<LawFileRecord> getFileByOrderId(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		Integer orderId = json.getInteger("orderId");
		if (orderId != null) {
			List<LawFileRecord> allLawFile = lawFileRecordService.findAllByOrderId(orderId);
			if (allLawFile != null && !allLawFile.isEmpty()) {
				return allLawFile;
			}
		}
		return null;
	}

	@RequestMapping("/updatePromoterInfo")
	public void updatePromoterInfo(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		if (json != null) {
			String openId = json.getString("openId");
			String nickName = json.getString("nickName");
			String avatarUrl = json.getString("avatarUrl");
			LawPromoter lawPromoter = lawPromoterService.findByOpenIdAndStatus(openId, Constant.DATA_STATUS_OK);
			if (lawPromoter != null && StringUtils.isNotBlank(nickName)) {
				lawPromoter.setPromoterName(nickName);
				lawPromoter.setAvatarUrl(avatarUrl);
				lawPromoterService.save(lawPromoter);
			}
		}
	}

	@RequestMapping("/chattest")
	public String test() {
		System.err.println("kelaode");
		return "jekaide";
	}

	@RequestMapping("/initChatData")
	public List<Chat> initChatData() {
		List<Chat> sessions = new ArrayList<>();
		Chat chat = new Chat();
		chat.setId("1");
		User user = new User();
		user.setImg("src/components/page/chat/dist/images/2.png");
		user.setName("发信方");
		chat.setUser(user);
		List<Message> messages = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Message message = new Message();
			if (i % 2 == 0) {
				message.setSelf(true);
				message.setContent("谢谢" + i);
			} else {
				message.setContent("你好" + i);
			}
			DateTime dateTime = new DateTime().plus(-i);
			message.setDate(dateTime.toString());
			messages.add(message);
		}
		chat.setMessages(messages);
		sessions.add(chat);

		//===================
		Chat chat2 = new Chat();
		chat2.setId("2");
		User user2 = new User();
		user2.setImg("src/components/page/chat/dist/images/3.jpg");
		user2.setName("收信方");
		chat2.setUser(user2);
		sessions.add(chat2);

		return sessions;
	}

	@RequestMapping("/getPromoter")
	public LawPromoter getPromoter(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		String openId = json.getString("openId");
		LawPromoter lawPromoter = lawPromoterService.findByOpenIdAndStatus(openId, TableStatusEnum.U.getCode());
		return lawPromoter;
	}

	/**
	 *获取总人数、涉案金额、
	 *总收入趋势
	 *		总收入
	 *		本月收入
	 *		0-12月收入分布
	 *总成单趋势
	 *		总成单
	 *		本月成单
	 *		0-12月总成单趋势
	 * @return 
	 */
	@RequestMapping("/getStatisticsData")
	public Map<String, Object> getStatisticsData(@RequestBody String data) {

		Map<String, Object> returnMap = new HashMap<>();
		JSONObject json = JSONObject.parseObject(data);
		String type = json.getString("type");
		if (type.equals("0")) {
			//总人数
			long totalPromoterNum = lawPromoterService.countByStatus(Constant.DATA_STATUS_OK);
			//获取涉案金额
			BigDecimal countInvolvingMoney = lawService.countInvolvingMoney();
			returnMap.put("totalPeople", totalPromoterNum);
			returnMap.put("involvedNum", countInvolvingMoney);
			
		}
		if (type.equals("income")) {
			//获取总收入趋势
			BigDecimal sumFeePayOrder = lawPayService.sumFeePayOrder();
			BigDecimal sumFeePayOrderByMonth = lawPayService.sumFeePayOrderByMonth();
			List<KeyValue> sumFeePayOrderByMonthGrp = lawPayService.sumFeePayOrderByMonthGrp();
			returnMap.put("total", sumFeePayOrder);
			returnMap.put("current", sumFeePayOrderByMonth);
			List<BigDecimal> collect = handleFillList(sumFeePayOrderByMonthGrp).stream().map(KeyValue::getMoney).collect(Collectors.toList());
			returnMap.put("data", collect);
		}

		if (type.equals("order")) {
			//获取总成单趋势
			Long countPayOrder = lawPayService.countPayOrder();
			Long countPayOrderByMonth = lawPayService.countPayOrderByMonth();
			List<KeyValue> countPayOrderByMonthGrp = lawPayService.countPayOrderByMonthGrp();
			returnMap.put("total", countPayOrder);
			returnMap.put("current", countPayOrderByMonth);
			List<BigInteger> collect = handleFillList(countPayOrderByMonthGrp).stream().map(KeyValue::getOrderNum).collect(Collectors.toList());
			returnMap.put("data", collect);
		}
		
		return returnMap;

	}
	
	public List<KeyValue> handleFillList(List<KeyValue> list) {
		if(ListUtil.isBlank(list)) list = new ArrayList<>();
		List<String> collect = list.stream().map(KeyValue::getDonetiem).collect(Collectors.toList());
		for(int i=1;i<13;i++) {
			String str = i+"";
			if(i<10)str = "0"+i;
			if(!collect.contains(str)) {
				list.add(new KeyValue(i+"",BigDecimal.valueOf(0),BigInteger.valueOf(0)));
			}
		}
		return list.stream().sorted(Comparator.comparing(KeyValue::getDonetiem)).collect(Collectors.toList());
	}

	@RequestMapping("/statisticsTest")
	public List<KeyValue> statisticsTest() {
		List<KeyValue> sumFeePayOrderByMonthGrp = lawPayService.countPayOrderByMonthGrp();
		return sumFeePayOrderByMonthGrp;
	}
	
	public void getPublicityLawByPage() {
		//PublicityLaw
	}
}
