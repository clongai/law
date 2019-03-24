package com.law.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wx_message")
public class WxMessage {

	private Long id;
	private String msgId;
	private String fromUser;
	private String toUser;
	private String msgType;
	private String content;
	private String mediaId;
	private Date createTime;
	private String picUrl;
	private Date doneTime;
	private Integer msgSource;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="msg_id")
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name="from_user")
	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	@Column(name="to_user")
	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	@Column(name="msg_type")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="media_id")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="pic_url")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(name="done_time")
	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	@Column(name="msg_source")
	public Integer getMsgSource() {
		return msgSource;
	}

	public void setMsgSource(Integer msgSource) {
		this.msgSource = msgSource;
	}

	@Override
	public String toString() {
		return "WxMessage [id=" + id + ", msgId=" + msgId + ", fromUser=" + fromUser + ", toUser=" + toUser + ", msgType=" + msgType + ", content=" + content + ", mediaId=" + mediaId + ", createTime=" + createTime + ", picUrl=" + picUrl + ", doneTime=" + doneTime + ", msgSource=" + msgSource + "]";
	}
	
	

}
