package com.law.common;

/**
 * 组件类型
 */
public enum ComponentType {
	SELECT("select"),
	TABLE("table"),
	DUAL_TABLE("dualTable"),
	OTHER("other"),
	;
	
	private String name;

	private ComponentType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
