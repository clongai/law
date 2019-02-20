package com.law.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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
import com.law.entity.LawFileRecord;
import com.law.entity.LawOrderExt;
import com.law.idworker.IdWorker;
import com.law.model.Law;
import com.law.model.LawCaseDropdownModel;
import com.law.model.LawOrder;
import com.law.model.LawPay;
import com.law.model.LawPerson;
import com.law.model.LawPromoter;
import com.law.model.LawServiceEntity;
import com.law.model.OrderPO;
import com.law.service.LawFileRecordService;
import com.law.service.LawOrderExtService;
import com.law.service.LawPersonService;
import com.law.service.LawPromoterService;
import com.law.service.LawService;
import com.law.util.FileUtil;


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
    public String uploadFileOrderId(@RequestParam("file") MultipartFile file,@RequestParam("orderId") Integer orderId) {
        try {
            String fileName = file.getOriginalFilename();
            if (!StringUtils.isBlank(fileName)) {

                String outFileName = idWorker.nextId() + "." + FileUtil.getFileType(fileName);
                File outFile = new File(uploadDir + outFileName);
                FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                if(orderId!=null) {
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
    public void downloadFile(HttpServletResponse response,@RequestParam("fileId") Integer fileId) {
    	if(fileId!=null) {
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
                }
                catch (Exception e) {
                    System.out.println("Download the song failed!");
                }
                finally {
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
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException {

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
                }
                catch (Exception e) {
                    System.out.println("Download the song failed!");
                }
                finally {
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
    public @ResponseBody
    LawOrder addLawOrder(@RequestBody Law law) {
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
        Page<LawOrder> returnList = lawService.queryLawOrderByStatus(status,serviceLevel, page, pageSize);
        if(returnList!=null) {
        	if(returnList.getContent()!=null) {
        	 @SuppressWarnings("deprecation")
			Pageable pageable = new PageRequest(page, pageSize);
        	 List<OrderPO> list = new ArrayList<>();
        	returnList.getContent().forEach(t->{
        		OrderPO op = new OrderPO();
        		BeanUtils.copyProperties(t, op);
        		op.setStatusVal(t.getStatus());
        		list.add(op);
        	});
        	 PageImpl<OrderPO> pageImpl= new PageImpl<OrderPO>(list,pageable,returnList.getTotalElements());
        	 return pageImpl;
        	}
        }
        return null;
    }


    @RequestMapping("/getLawOrder")
    public Law getLawOrder(@RequestBody String data) {
        JSONObject json = JSONObject.parseObject(data);
        Integer orderId = json.getInteger("orderId");
        return lawService.queryLaw(orderId);
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
			if(lawOrderExt.getFileList()!=null) {
				orderPo.setFileList(lawOrderExt.getFileList().replaceAll("\n", ""));
			}

		}
		//获取案件当事人信息
		 List<LawPerson> partyPerson = lawPersonService.findAllByOrderIdAndPersonTypeOrderByPersonId(orderId, "1");
		 if(partyPerson!=null&&!partyPerson.isEmpty()) {
			 String personName = partyPerson.get(0).getPersonName();
			 orderPo.setPartyPersonName(personName);
		 }
		
		return orderPo;
	}
    
    @RequestMapping("/customeRefuseOrder")
    public LawOrder customeRefuseOrder(@RequestBody String data) {
    	JSONObject json = JSONObject.parseObject(data);
    	Integer orderId = json.getInteger("orderId");
    	String refuseReason = json.getString("refuseReason");
    	LawOrder lawOrder = lawService.queryLawOrder(orderId);
    	if(lawOrder!=null) {
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
    	if(orderId!=null) {
    		List<LawFileRecord> allLawFile = lawFileRecordService.findAllByOrderId(orderId);
    		if(allLawFile!=null&&!allLawFile.isEmpty()) {
    			return allLawFile;
    		}
    	}
    	return null;
    }
    
    @RequestMapping("/updatePromoterInfo")
    public void updatePromoterInfo(@RequestBody String data) {
    	JSONObject json = JSONObject.parseObject(data);
    	if(json!=null) {
    		String openId = json.getString("openId");
    		String nickName = json.getString("nickName");
    		LawPromoter lawPromoter = lawPromoterService.findByOpenIdAndStatus(openId, Constant.DATA_STATUS_OK);
    		if(lawPromoter!=null&&StringUtils.isNotBlank(nickName)) {
    			lawPromoter.setPromoterName(nickName);
    			lawPromoterService.save(lawPromoter);
    		}
    	}
    }
    
}
