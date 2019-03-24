package com.law.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class AjaxResult implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer status;
	private String message;
	private Object data;
	
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	public static final int ERROR = -1;
	
	public AjaxResult() {
		super();
	}

	public AjaxResult(Integer status, Object data) {
		super();
		this.status = status;
		this.data = data;	
	}
	
	public AjaxResult(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public AjaxResult(Integer status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static AjaxResult success(Object data) {
		return new AjaxResult(SUCCESS, data);
	}
	
	public static AjaxResult fail(Object data) {
		return new AjaxResult(FAIL, data);
	}
	
	public static AjaxResult error(Object data) {
		return new AjaxResult(ERROR, data);
	}
	
	public static AjaxResult fail(String message) {
		return new AjaxResult(FAIL, message);
	}
	
	public static AjaxResult error(String message) {
		return new AjaxResult(ERROR, message);
	}
	
	public static AjaxResult fail(ErrorCode errorCode) {
		return new AjaxResult(FAIL, '['+errorCode.getStatus()+']'+errorCode.getMessage());
	}
	
	public static AjaxResult error(ErrorCode errorCode) {
		return new AjaxResult(ERROR, '['+errorCode.getStatus()+']'+errorCode.getMessage());
	}
	
	public static String successJson(String key, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, data);
		return successJson(map);
	}
	
	public static String successJson() {
		return JSONObject.toJSONString(success(null));
	}
	
	public static String successJson(Object data) {
		return JSONObject.toJSONString(success(data));
	}
	
	public static String failJson(Object data) {
		return JSONObject.toJSONString(fail(data));
	}
	
	public static String errorJson(Object data) {
		return JSONObject.toJSONString(fail(data));
	}
	
	public static String failJson(String message) {
		return JSONObject.toJSONString(fail(message));
	}
	
	public static String errorJson(String message) {
		return JSONObject.toJSONString(fail(message));
	}
	
	public static String failJson(ErrorCode errorCode) {
		return JSONObject.toJSONString(fail('['+errorCode.getStatus()+']'+errorCode.getMessage()));
	}
	
	public static String errorJson(ErrorCode errorCode) {
		return JSONObject.toJSONString(error('['+errorCode.getStatus()+']'+errorCode.getMessage()));
	}
}
