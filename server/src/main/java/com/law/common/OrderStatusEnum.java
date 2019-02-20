package com.law.common;

public enum OrderStatusEnum {

	ORDER_CREATE("创建订单","0"),
	PHONE_APPOINTMENT("电话预约","11"),
	CALL_PHONE("电话沟通","12"),
	INTERVIEWE_WAIT_PAY("面谈待付款","1"),
	INTERVIEWE_WAIT_BACK("面谈后待反馈","2"),
	INTERVIEWE_WAIT_BACK_OK("面谈反馈接受","13"),
	INTERVIEWE_WAIT_BACK_ADD_DATA("面谈反馈添加材料","14"),
	CUSTOMER_ADD_DATA("客户添加材料","15"),
	INTERVIEWE_REFUSE("面谈反馈拒绝","6"),
	MARKET_REFUSE_WAIT_ADD_DATA("市场部拒绝待客户添加资料","3"),
	CUSTOMER_ADD_MARKET_DATA("客户已完善市场部所需资料","16"),
	AGENT_MONEY_WAIT_PAY("代理金额待付款","4"),
	MARKET_END("市场部结束订单","17"),
	CUSTOMER_END("客户结束订单","18"),
	FW_EXPERT_TEAM("法务转专家团队","7"),
	//Interview
	E("E","E");
	
	private String name;
	private String code;
	
	private OrderStatusEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
