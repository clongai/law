package com.law.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "law_order_ext")
public class LawOrderExt {
	private Date phoneStartTime1;
	private Date phoneStartTime2;
	private Date phoneStartTime3;
	private Date phoneEndTime1;
	private Date phoneEndTime2;
	private Date phoneEndTime3;
	private Date acceptStartTime1;
	private Date acceptStartTime2;
	private Date acceptStartTime3;
	private Date acceptEndTime1;
	private Date acceptEndTime2;
	private Date acceptEndTime3;
	private Integer id;
	private Integer orderId;
	private Integer selected;
	private Integer faceSelected;
	private Date customEndTime;
	private Date customStratTime;
	private String fileList;
	private String feedbackOpinion;
	
	
	@Column(name="feedback_opinion")
	public String getFeedbackOpinion() {
		return feedbackOpinion;
	}

	public void setFeedbackOpinion(String feedbackOpinion) {
		this.feedbackOpinion = feedbackOpinion;
	}

	@Column(name="file_list")
	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}

	@Column(name = "face_selected")
	public Integer getFaceSelected() {
		return faceSelected;
	}

	public void setFaceSelected(Integer faceSelected) {
		this.faceSelected = faceSelected;
	}

	@Column(name = "accept_start_time1")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptStartTime1() {
		return acceptStartTime1;
	}

	public void setAcceptStartTime1(Date acceptStartTime1) {
		this.acceptStartTime1 = acceptStartTime1;
	}

	@Column(name = "accept_start_time2")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptStartTime2() {
		return acceptStartTime2;
	}

	public void setAcceptStartTime2(Date acceptStartTime2) {
		this.acceptStartTime2 = acceptStartTime2;
	}

	@Column(name = "accept_start_time3")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptStartTime3() {
		return acceptStartTime3;
	}

	public void setAcceptStartTime3(Date acceptStartTime3) {
		this.acceptStartTime3 = acceptStartTime3;
	}

	@Column(name = "accept_end_time1")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptEndTime1() {
		return acceptEndTime1;
	}

	public void setAcceptEndTime1(Date acceptEndTime1) {
		this.acceptEndTime1 = acceptEndTime1;
	}

	@Column(name = "accept_end_time2")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptEndTime2() {
		return acceptEndTime2;
	}

	public void setAcceptEndTime2(Date acceptEndTime2) {
		this.acceptEndTime2 = acceptEndTime2;
	}

	@Column(name = "accept_end_time3")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptEndTime3() {
		return acceptEndTime3;
	}

	public void setAcceptEndTime3(Date acceptEndTime3) {
		this.acceptEndTime3 = acceptEndTime3;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	@Column(name = "custom_end_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getCustomEndTime() {
		return customEndTime;
	}

	public void setCustomEndTime(Date customEndTime) {
		this.customEndTime = customEndTime;
	}

	@Column(name = "custom_start_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getCustomStratTime() {
		return customStratTime;
	}

	public void setCustomStratTime(Date customStratTime) {
		this.customStratTime = customStratTime;
	}

	@Column(name = "phone_start_time1")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getPhoneStartTime1() {
		return phoneStartTime1;
	}

	public void setPhoneStartTime1(Date phoneStartTime1) {
		this.phoneStartTime1 = phoneStartTime1;
	}

	@Column(name = "phone_start_time2")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getPhoneStartTime2() {
		return phoneStartTime2;
	}

	public void setPhoneStartTime2(Date phoneStartTime2) {
		this.phoneStartTime2 = phoneStartTime2;
	}

	@Column(name = "phone_start_time3")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getPhoneStartTime3() {
		return phoneStartTime3;
	}

	public void setPhoneStartTime3(Date phoneStartTime3) {
		this.phoneStartTime3 = phoneStartTime3;
	}

	@Column(name = "phone_end_time1")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getPhoneEndTime1() {
		return phoneEndTime1;
	}

	public void setPhoneEndTime1(Date phoneEndTime1) {
		this.phoneEndTime1 = phoneEndTime1;
	}

	@Column(name = "phone_end_time2")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getPhoneEndTime2() {
		return phoneEndTime2;
	}

	public void setPhoneEndTime2(Date phoneEndTime2) {
		this.phoneEndTime2 = phoneEndTime2;
	}

	@Column(name = "phone_end_time3")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getPhoneEndTime3() {
		return phoneEndTime3;
	}

	public void setPhoneEndTime3(Date phoneEndTime3) {
		this.phoneEndTime3 = phoneEndTime3;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "order_id")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}
