package com.law.common;

public enum ErrorCode {

	/**
	 * 系统级编码
	 */
	SUCCESS("0000", "请求成功"), ERROR("0001", "请求失败"), TIMEOUT("0002", "请求超时")
	, AUTH_FAIL("0003", "认证失败"),
	PARAM_NULL("0004", "参数为空"),
	WORK_NO_NOT_EXIST("0005", "工号信息不存在"),
	DATA_TIMEOUT("0006", "工号信息不存在");

	private String status;
	private String message;

	private ErrorCode(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getMessage(String[] args) {
		int index = 0;
		String tempMsg = message;
		for (String arg : args) {
			tempMsg = tempMsg.replace("{" + index++ + "}", String.valueOf(arg));
		}
		return tempMsg;
	}

	public String getMessage(ErrorCode[] args) {
		int index = 0;
		String tempMsg = message;
		for (ErrorCode arg : args) {
			tempMsg = tempMsg.replace("{" + index++ + "}", arg.getMessage());
		}
		return tempMsg;
	}

}
