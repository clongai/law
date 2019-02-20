package com.law.common;

public enum RefuseStatusEnum {
	LACK_EVIDENCE("缺少证据","0"),
	OTHER_REASON("其他原因","1"),
	//Interview
	E("E","E");
	
	private String name;
	private String code;
	
	private RefuseStatusEnum(String name, String code) {
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
