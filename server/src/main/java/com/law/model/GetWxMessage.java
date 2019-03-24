package com.law.model;

import java.util.Date;

public class GetWxMessage {

	private String Content;
	private Date CreateTime;
	private String FromUserName;
	private String MsgId;
	private String MsgType;
	private String ToUserName;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	@Override
	public String toString() {
		return "WxMessage [Content=" + Content + ", CreateTime=" + CreateTime + ", FromUserName=" + FromUserName + ", MsgId=" + MsgId + ", MsgType=" + MsgType + ", ToUserName=" + ToUserName + "]";
	}

}
