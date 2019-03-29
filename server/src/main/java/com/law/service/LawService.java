package com.law.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.google.common.base.Splitter;
import com.law.common.Constant;
import com.law.common.OrderStatusEnum;
import com.law.common.RefuseStatusEnum;
import com.law.entity.LawOrderExt;
import com.law.entity.OrderQuote;
import com.law.idworker.IdWorker;
import com.law.model.Law;
import com.law.model.LawCase;
import com.law.model.LawCaseDropdownModel;
import com.law.model.LawOrdSerRel;
import com.law.model.LawOrder;
import com.law.model.LawOrderHis;
import com.law.model.LawPay;
import com.law.model.LawPerson;
import com.law.model.LawPromoter;
import com.law.model.LawServiceEntity;
import com.law.model.OrderPO;
import com.law.model.SysUser;
import com.law.repository.LawCaseRepository;
import com.law.repository.LawChannelRepository;
import com.law.repository.LawOrdSerRelRepository;
import com.law.repository.LawOrderExtRepository;
import com.law.repository.LawOrderHisRepository;
import com.law.repository.LawOrderRepository;
import com.law.repository.LawPayRepository;
import com.law.repository.LawPersonRepository;
import com.law.repository.LawPromoterRepository;
import com.law.repository.LawServiceRepository;
import com.law.repository.SysUserRepository;
import com.law.wxpay.LawWXPayConfig;

@Service
public class LawService {

	@Autowired
	LawOrderRepository lawOrderRepository;
	@Autowired
	LawPersonRepository lawPersonRepository;
	@Autowired
	LawServiceRepository lawServiceRepository;
	@Autowired
	LawPayRepository lawPayRepository;
	@Autowired
	LawOrderHisRepository lawOrderHisRepository;
	@Autowired
	LawCaseRepository lawCaseRepository;
	@Autowired
	SysUserRepository sysUserRepository;
	@Autowired
	LawChannelRepository lawChannelRepository;
	@Autowired
	LawPromoterRepository lawPromoterRepository;
	@Autowired
	LawOrdSerRelRepository lawOrdSerRelRepository;

	@Autowired
	LawOrderExtRepository lawOrderExtRepository;

	@Autowired
	LawWXPayConfig wxPayConfig;

	@Autowired
	IdWorker idWorker;
	@Autowired
	LawServiceService lawServiceService;
	@Autowired
	LawOrderExtService lawOrderExtService;
	@Autowired
	OrderQuoteService orderQuoteService;

	public LawOrder addLawOrder(Law law) {
		LawOrder lawOrder = law.getLawOrder();
		lawOrder.setDoneCode(idWorker.nextId());
		lawOrder.setDoneDate(new Timestamp(System.currentTimeMillis()));
		if (lawOrder.getOrderId() != null) {
			LawOrder queryLawOrder = queryLawOrder(lawOrder.getOrderId());
			if (queryLawOrder != null) {
				if (lawOrder.getServiceId() == null) {
					lawOrder.setServiceId(queryLawOrder.getServiceId());
				}
			}
			//客户完善法务部所需资料
			if (OrderStatusEnum.INTERVIEWE_WAIT_BACK_ADD_DATA.getCode().equals(lawOrder.getStatus())) {
				lawOrder.setStatus(OrderStatusEnum.CUSTOMER_ADD_DATA.getCode());
			}
			//客户完善法务部所需资料重复提交
			if (OrderStatusEnum.CUSTOMER_ADD_DATA.getCode().equals(lawOrder.getStatus())) {
				lawOrder.setStatus(OrderStatusEnum.CUSTOMER_ADD_DATA.getCode());
			}
			//客户完善市场部所需资料
			if (OrderStatusEnum.MARKET_REFUSE_WAIT_ADD_DATA.getCode().equals(lawOrder.getStatus())) {
				lawOrder.setStatus(OrderStatusEnum.CUSTOMER_ADD_MARKET_DATA.getCode());
			}
			//客户完善市场部所需资料重复提交
			if (OrderStatusEnum.CUSTOMER_ADD_MARKET_DATA.getCode().equals(lawOrder.getStatus())) {
				lawOrder.setStatus(OrderStatusEnum.CUSTOMER_ADD_MARKET_DATA.getCode());
			}
		}else {
			lawOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		lawOrder = lawOrderRepository.saveAndFlush(lawOrder);

		List<LawPerson> lawPersons1 = law.getLawPerson1();
		for (LawPerson lawPerson : lawPersons1) {
			lawPerson.setOrderId(lawOrder.getOrderId());
		}
		lawPersonRepository.saveAll(lawPersons1);

		List<LawPerson> lawPersons2 = law.getLawPerson2();
		for (LawPerson lawPerson : lawPersons2) {
			lawPerson.setOrderId(lawOrder.getOrderId());
		}
		lawPersonRepository.saveAll(lawPersons2);

		List<LawPerson> lawPersons3 = law.getLawPerson3();
		for (LawPerson lawPerson : lawPersons3) {
			lawPerson.setOrderId(lawOrder.getOrderId());
		}
		lawPersonRepository.saveAll(lawPersons3);

		return lawOrder;
	}

	public LawOrder saveLawOrder(LawOrder lawOrder) {
		return lawOrderRepository.saveAndFlush(lawOrder);
	}

	public List<LawOrder> queryLawOrderByOpenId(String openId) {
		return lawOrderRepository.findAllByOpenIdOrderByOrderIdDesc(openId);
	}

	public LawOrder queryLawOrder(Integer orderId) {
		return lawOrderRepository.findOneByOrderId(orderId);
	}

	public Law queryLaw(Integer orderId, Integer orginazation) {
		LawOrder lawOrder = lawOrderRepository.findOneByOrderId(orderId);
		List<LawPerson> lawPeople1 = lawPersonRepository.findAllByOrderIdAndPersonTypeOrderByPersonId(orderId, "1");
		List<LawPerson> lawPeople2 = lawPersonRepository.findAllByOrderIdAndPersonTypeOrderByPersonId(orderId, "2");
		List<LawPerson> lawPeople3 = lawPersonRepository.findAllByOrderIdAndPersonTypeOrderByPersonId(orderId, "3");
		Law law = new Law();
		OrderPO orderPo = new OrderPO();
		BeanUtils.copyProperties(lawOrder, orderPo);
		LawOrderExt lawOrderExt = lawOrderExtRepository.findByOrderId(orderId);
		if (lawOrderExt != null) {
			Date customStartTime = null;
			Date customEndTime = null;
			JSONObject parseObject = JSONObject.parseObject(JSONObject.toJSONString(lawOrderExt));
			if (lawOrderExt.getSelected() != null) {
				if (lawOrderExt.getSelected() == 3) {
					customStartTime = lawOrderExt.getCustomStratTime();
					customEndTime = lawOrderExt.getCustomEndTime();
				} else {
					customStartTime = parseObject.getDate("phoneStartTime" + (lawOrderExt.getSelected() + 1));
					customEndTime = parseObject.getDate("phoneEndTime" + (lawOrderExt.getSelected() + 1));
				}
			}

			orderPo.setCustomStartTime(customStartTime);
			orderPo.setCustomEndTime(customEndTime);
			orderPo.setAcceptEndTime1(lawOrderExt.getAcceptEndTime1());
			orderPo.setAcceptEndTime2(lawOrderExt.getAcceptEndTime2());
			orderPo.setAcceptEndTime3(lawOrderExt.getAcceptEndTime3());
			orderPo.setAcceptStartTime1(lawOrderExt.getAcceptStartTime1());
			orderPo.setAcceptStartTime2(lawOrderExt.getAcceptStartTime1());
			orderPo.setAcceptStartTime3(lawOrderExt.getAcceptStartTime3());
			orderPo.setFeedbackOpinion(lawOrderExt.getFeedbackOpinion());
			orderPo.setAnalysisResult(lawOrderExt.getAnalysisResult());
			OrderQuote orderQuote = null;
			if(lawOrder.getStatus().equals(OrderStatusEnum.AGENT_MONEY_WAIT_PAY.getCode())) {
				orderQuote = orderQuoteService.findByOrderIdAndType(orderId, Constant.ROLE_SC);
			}else {
				orderQuote = orderQuoteService.findByOrderIdAndType(orderId, Constant.ROLE_FW);
			}
			if(orderQuote!=null) {
				orderPo.setQuoteA(orderQuote.getQuoteA());
				orderPo.setQuoteB(orderQuote.getQuoteB());
				orderPo.setQuoteC(orderQuote.getQuoteC());
			}
		}
		if (lawOrder.getAgentMoney() != null) {
			orderPo.setAgentMoneyPay(lawOrder.getAgentMoney());
			orderPo.setTaxAgentMoneyPay(lawOrder.getAgentMoney());
		}
		law.setLawOrder(lawOrder);
		law.setLawPerson1(lawPeople1);
		law.setLawPerson2(lawPeople2);
		law.setLawPerson3(lawPeople3);
		law.setOrderPO(orderPo);
		return law;
	}

	public Integer countLawOrderByStatus(String status) {
		return lawOrderRepository.countByStatus(status);
	}

	public Page<LawOrder> queryLawOrderByStatus(String status, String serviceLevel, int page, int pageSize) {
		return lawOrderRepository.findAll(new Specification<LawOrder>() {
			@Override
			public Predicate toPredicate(Root<LawOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (!StringUtils.isEmpty(status) && status.contains(Constant.ALL_ORDER_SEP_SIGN)) {
					In<String> in = criteriaBuilder.in(root.get("status"));
					Splitter.on(",").trimResults().splitToList(status).forEach(t -> {
						in.value(t);
					});
					list.add(in);
				} else {
					list.add(criteriaBuilder.equal(root.get("status").as(String.class), status));
				}

				if (!StringUtils.isEmpty(serviceLevel) && !serviceLevel.equals("null")) {
					//根据serviceLevel获取serviceID
					List<Integer> splitToList = new ArrayList<>();
					Splitter.on(",").trimResults().splitToList(serviceLevel).forEach(t -> splitToList.add(Integer.parseInt(t)));
					List<LawServiceEntity> allLawService = lawServiceService.findAllByServiceLevelInAndStatus(splitToList, Constant.DATA_STATUS_OK);
					In<Integer> serviceIdIn = criteriaBuilder.in(root.get("serviceId"));
					allLawService.forEach(s -> serviceIdIn.value(s.getServiceId()));
					//然后根据serviceID获取订单记录
					list.add(serviceIdIn);
				}

				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, PageRequest.of(page, pageSize, Sort.Direction.DESC, "orderId"));
	}

	public List<LawServiceEntity> queryLawService() {
		return lawServiceRepository.findAllService();
	}

	public LawPay addPayOrder(LawPay lawPay) {
		lawPayRepository.save(lawPay);
		return lawPay;
	}

	public LawOrderHis move2LawOrderHis(LawOrder lawOrder) {
		LawOrderHis lawOrderHis = new LawOrderHis();
		BeanUtils.copyProperties(lawOrder, lawOrderHis);
		lawOrderHisRepository.save(lawOrderHis);
		return lawOrderHis;
	}

	public LawPay updatePayStatus(String tradeNo, int tradeState) {
		LawPay lawPay = lawPayRepository.findOneByOutTradeNo(tradeNo);
		lawPay.setTradeState(tradeState);
		lawPayRepository.save(lawPay);
		return lawPay;
	}

	public boolean deal4PaySuccess(String tradeNo, String doneCode,String terms_type) {
		try {
			LawPay lawPay = lawPayRepository.findOneByOutTradeNo(tradeNo);
			int orderId = lawPay.getOrderId();
			LawOrder lawOrder = queryLawOrder(orderId);
			String status = lawOrder.getStatus();
			move2LawOrderHis(lawOrder);
			lawOrder.setDoneCode(doneCode);
			lawOrder.setDoneDate(new Timestamp(System.currentTimeMillis()));
			if (status.equalsIgnoreCase("1")) {
				lawOrder.setServiceId(lawPay.getServiceId());
				lawOrder.setStatus("2");//状态改为初审通过已支付
			} else if (status.equalsIgnoreCase("4")) {
				lawOrder.setStatus("5");//状态改为二审通过已支付
				lawOrder.setTermsType(terms_type);
			} else if (status.equalsIgnoreCase("8")) {
				lawOrder.setStatus("9");//状态改为补充二审通过已付款
			}
			saveLawOrder(lawOrder);

			lawPay.setDoneCode(idWorker.nextId());
			lawPay.setDoneDate(new Timestamp(System.currentTimeMillis()));
			updatePayStatus(tradeNo, 1);
			addPayOrder(lawPay);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Map<String, String> unifiedOrder(Map<String, String> map) throws Exception {
		WXPay wxPay = new WXPay(wxPayConfig, WXPayConstants.SignType.MD5);
		return wxPay.unifiedOrder(map);
	}

	public List<LawCaseDropdownModel> findCaseByName(String name, String type, String parent) {
		List<LawCaseDropdownModel> list = new ArrayList<>();
		List<LawCase> caseList = new ArrayList<>();
		if (type.equalsIgnoreCase("1")) {
			caseList = lawCaseRepository.findBaseLevelByName(name);
		} else if (type.equalsIgnoreCase("2") && parent.equalsIgnoreCase("")) {
			caseList = lawCaseRepository.findSubLevelByName(name);
		} else if (type.equalsIgnoreCase("2") && !parent.equalsIgnoreCase("")) {
			LawCase parentCase = lawCaseRepository.findLawCaseByCaseName(parent);
			caseList = lawCaseRepository.findSubLevelByNameAndParent(name, parentCase.getCaseId());
		}
		for (LawCase lawCase : caseList) {
			LawCaseDropdownModel model = new LawCaseDropdownModel();
			model.setCaseID(lawCase.getCaseId());
			model.setValue(lawCase.getCaseName());
			if (type.equalsIgnoreCase("2")) {
				LawCase parentCase = lawCaseRepository.findAllByCaseId(lawCase.getParentCase());
				model.setParent(parentCase.getCaseName());
			}
			list.add(model);
		}
		return list;
	}

	public List<LawCaseDropdownModel> findAllBaseCase() {
		List<LawCaseDropdownModel> list = new ArrayList<>();
		List<LawCase> caseList = new ArrayList<>();
		caseList = lawCaseRepository.findAllBaseCase();
		for (LawCase lawCase : caseList) {
			LawCaseDropdownModel model = new LawCaseDropdownModel();
			model.setCaseID(lawCase.getCaseId());
			model.setValue(lawCase.getCaseName());
			list.add(model);
		}
		return list;
	}

	public LawOrdSerRel bindService(int orderId, int serviceId) throws Exception {
		LawServiceEntity service = lawServiceRepository.getByServiceId(serviceId);
		String serviceLevel = String.valueOf(service.getServiceLevel());
		LawOrdSerRel lawOrdSerRel = new LawOrdSerRel();
		lawOrdSerRel.setOrderId(orderId);
		lawOrdSerRel.setServiceId(serviceId);
		//根据咨询档次随机分配法务人员（审核人员）
		List<SysUser> userList = sysUserRepository.getAllByServiceLevelLike("%" + serviceLevel + "%");
		Random random = new Random();
		int n = random.nextInt(userList.size());
		SysUser auditor = userList.get(n);
		lawOrdSerRel.setAuditor(auditor.getUserId());
		lawOrdSerRel.setCreateTime(new Timestamp(System.currentTimeMillis()));
		lawOrdSerRelRepository.save(lawOrdSerRel);
		return lawOrdSerRel;
	}

	public Map<String, Object> bindPromoter(String openId, String parentOpenId) {
		Map<String, Object> map = new HashMap<>();
		if (isPromoter(openId)) {
			map.put("status", "registered");
			map.put("promoter", lawPromoterRepository.findOneByOpenIdAndStatus(openId, "U"));
			return map;
		}
		LawPromoter parentPromoter = lawPromoterRepository.findOneByOpenIdAndStatus(parentOpenId, "U");
		int channelId = parentPromoter.getChannelId();
		LawPromoter promoter = new LawPromoter();
		promoter.setChannelId(channelId);
		promoter.setParentPromoterId(parentPromoter.getPromoterId());
		promoter.setOpenId(openId);
		promoter.setStatus("U");
		promoter.setCreateTime(new Date(System.currentTimeMillis()));
		lawPromoterRepository.save(promoter);
		map.put("status", "new");
		map.put("promoter", promoter);
		return map;
	}

	public boolean bindPromoterPhone(String openId, String phone) {
		LawPromoter promoter = lawPromoterRepository.findOneByOpenIdAndStatus(openId, "U");
		if (promoter != null) {
			String promoterTel = promoter.getPromoterTel();
			if (StringUtils.isEmpty(promoterTel)) {
				promoter.setPromoterTel(phone);
				lawPromoterRepository.save(promoter);
			}
			return true;
		}
		return false;
	}

	public boolean isPromoter(String openId) {
		LawPromoter promoter = lawPromoterRepository.findOneByOpenIdAndStatus(openId, "U");
		if (promoter != null) {
			return true;
		}
		return false;
	}

	@Transactional
	public LawOrder accpetLawOrder(String data) {
		JSONObject json = JSONObject.parseObject(data);
		LawOrder lawOrder = json.getObject("lawOrder", LawOrder.class);
		Integer orginazation = json.getInteger("orginazation");
		LawOrder order = queryLawOrder(lawOrder.getOrderId());
		move2LawOrderHis(order);
		order.setDoneCode(idWorker.nextId());
		order.setDoneDate(new Timestamp(System.currentTimeMillis()));
		//获取当前订单状态
		switch (orginazation) {
		case 1:
			if (OrderStatusEnum.ORDER_CREATE.getCode().equals(order.getStatus()) || OrderStatusEnum.CUSTOMER_ADD_MARKET_DATA.getCode().equals(order.getStatus())) {
				order.setStatus(OrderStatusEnum.PHONE_APPOINTMENT.getCode());//初审通过待电话预约
				/*LawOrderExt lawOrderExt = json.getObject("lawOrder", LawOrderExt.class);
				lawOrderExtService.save(lawOrderExt);*/
				return order;
			}
			if (OrderStatusEnum.CALL_PHONE.getCode().equals(order.getStatus())) {
				order.setStatus(OrderStatusEnum.INTERVIEWE_WAIT_PAY.getCode());//二审通过待付款
				//增加客户预约面谈时间数据
				LawOrderExt lawOrderExt = json.getObject("lawOrder", LawOrderExt.class);
				//获取已有的扩展数据对象
				LawOrderExt findByOrderId = lawOrderExtService.findByOrderId(order.getOrderId());
				findByOrderId.setAcceptEndTime1(lawOrderExt.getAcceptEndTime1());
				findByOrderId.setAcceptStartTime1(lawOrderExt.getAcceptStartTime1());
				/*findByOrderId.setAcceptEndTime2(lawOrderExt.getAcceptEndTime2());
				findByOrderId.setAcceptEndTime3(lawOrderExt.getAcceptEndTime3());
				findByOrderId.setAcceptStartTime2(lawOrderExt.getAcceptStartTime1());
				findByOrderId.setAcceptStartTime3(lawOrderExt.getAcceptStartTime3());*/
				order.setAcceptContracts(lawOrder.getAcceptContracts());//联系人
				order.setAcceptPhone(lawOrder.getAcceptPhone());//联系电话
				order.setAcceptAddress(lawOrder.getAcceptAddress());//联系地址
				order.setAcceptRemark(lawOrder.getAcceptRemark());//补充信息
				findByOrderId.setFaceSelected(1);//需求变动，其他的字段不需要了，默认选择第一个
				lawOrderExtService.save(findByOrderId);
			}
			if (OrderStatusEnum.INTERVIEWE_WAIT_BACK_OK.getCode().equals(order.getStatus())) {
				order.setStatus(OrderStatusEnum.AGENT_MONEY_WAIT_PAY.getCode());//通过待付款
				//更新代理金额
				//order.setAgentMoney(lawOrder.getAgentMoney());
				OrderQuote oq = json.getObject("lawOrder", OrderQuote.class);
				oq.setType(orginazation);
				oq.setStatus(Constant.DATA_STATUS_OK);
				orderQuoteService.save(oq);
			}
			break;
		case 2:
			//order.setStatus("4");//二审通过待付款
			if (OrderStatusEnum.INTERVIEWE_WAIT_BACK.getCode().equals(order.getStatus())) {
				order.setStatus(OrderStatusEnum.INTERVIEWE_WAIT_BACK_OK.getCode());//面谈反馈接受
				LawOrderExt lawOrderExt = json.getObject("lawOrder", LawOrderExt.class);
				LawOrderExt findByOrderId = lawOrderExtService.findByOrderId(order.getOrderId());
				//findByOrderId.setFaceSelected(lawOrderExt.getFaceSelected());
				findByOrderId.setFaceSelected(1);
				findByOrderId.setFileList(lawOrderExt.getFileList());
				findByOrderId.setAnalysisResult(lawOrderExt.getAnalysisResult());
				order.setInvolvingMoney(lawOrder.getInvolvingMoney());
				order.setCaseBaseLevel(lawOrder.getCaseBaseLevel());
				order.setCaseSubLevel(lawOrder.getCaseSubLevel());
				lawOrderExtService.save(findByOrderId);
				//保存报价数据
				OrderQuote oq = json.getObject("lawOrder", OrderQuote.class);
				oq.setType(orginazation);
				oq.setStatus(Constant.DATA_STATUS_OK);
				orderQuoteService.save(oq);
				return order;
			}
			if (OrderStatusEnum.CUSTOMER_ADD_DATA.getCode().equals(order.getStatus())) {
				order.setStatus(OrderStatusEnum.INTERVIEWE_WAIT_BACK_OK.getCode());
			}
			break;
		case 3:
			if (OrderStatusEnum.FW_EXPERT_TEAM.getCode().equals(order.getStatus())) {
				order.setStatus(OrderStatusEnum.INTERVIEWE_WAIT_BACK.getCode());//专家反馈后继续走到法务部，服务可以看到专家反馈的信息
				//补充市场部反馈消息
				LawOrderExt lawOrderExt = lawOrderExtService.findByOrderId(order.getOrderId());
				LawOrderExt lawOrderExtByJson = json.getObject("lawOrder", LawOrderExt.class);
				System.err.println(lawOrderExtByJson.getFeedbackOpinion());
				lawOrderExt.setFeedbackOpinion(lawOrderExtByJson.getFeedbackOpinion());
				lawOrderExtService.save(lawOrderExt);
			}
			//order.setStatus("8");//补充二审通过待付款
			break;
		}
		order.setAcceptDate(lawOrder.getAcceptDate());

		return saveLawOrder(order);
	}

	@Transactional
	public LawOrder refuseLawOrder(String data) {
		JSONObject json = JSONObject.parseObject(data);
		LawOrder lawOrder = json.getObject("lawOrder", LawOrder.class);
		String refuseReasonLabel = null;
		String fileList = null;
		JSONObject jsonObject = json.getJSONObject("lawOrder");
		if (jsonObject != null) {
			refuseReasonLabel = jsonObject.getString("refuseReasonLabel");
			fileList = jsonObject.getString("fileList");
		}
		Integer orginazation = json.getInteger("orginazation");
		LawOrder order = queryLawOrder(lawOrder.getOrderId());
		move2LawOrderHis(order);
		order.setDoneCode(idWorker.nextId());
		order.setDoneDate(new Timestamp(System.currentTimeMillis()));
		switch (orginazation) {
		case 1:
			if (RefuseStatusEnum.LACK_EVIDENCE.getCode().equals(refuseReasonLabel))
				order.setStatus(OrderStatusEnum.MARKET_REFUSE_WAIT_ADD_DATA.getCode());//市场部需要客户补充资料
			if (RefuseStatusEnum.OTHER_REASON.getCode().equals(refuseReasonLabel))
				order.setStatus(OrderStatusEnum.MARKET_END.getCode());//市场部结束订单
			break;
		case 2:
			if (OrderStatusEnum.INTERVIEWE_WAIT_BACK.getCode().equals(order.getStatus())) {
				if (RefuseStatusEnum.LACK_EVIDENCE.getCode().equals(refuseReasonLabel)) {
					order.setStatus(OrderStatusEnum.INTERVIEWE_WAIT_BACK_ADD_DATA.getCode());//待补充资料
					LawOrderExt lawOrderExt = lawOrderExtService.findByOrderId(lawOrder.getOrderId());
					if (lawOrderExt != null) {
						lawOrderExt.setFileList(fileList);
						lawOrderExtService.save(lawOrderExt);
					}
				}
				if (RefuseStatusEnum.OTHER_REASON.getCode().equals(refuseReasonLabel))
					order.setStatus(OrderStatusEnum.INTERVIEWE_REFUSE.getCode());//待补充资料
				break;
			}
			order.setStatus("6");//二审不通过
			break;
		case 3:
			order.setStatus("10");//补充二审不通过
			break;
		}
		order.setRefuseReason(lawOrder.getRefuseReason());
		return saveLawOrder(order);

	}
	
	
	public BigDecimal countInvolvingMoney() {
		return lawOrderRepository.countInvolvingMoney();
	}
}
