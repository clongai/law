package com.law.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.law.annotation.EnumField;

public class OrderPO extends LawOrder {

	private Date customStartTime;
	private Date customEndTime;
	private BigDecimal taxAgentMoneyPay;
	private BigDecimal agentMoneyPay;
	private Date acceptStartTime1;
	private Date acceptStartTime2;
	private Date acceptStartTime3;
	private Date acceptEndTime1;
	private Date acceptEndTime2;
	private Date acceptEndTime3;
	private Date acceptEndTime;
	private Date acceptStartTime;
	private String fileList;
	@EnumField(codeType = "order.status")
	private String statusVal;

	private String partyPersonName;// 当事人名称
	private String feedbackOpinion;

	public String getFeedbackOpinion() {
		return feedbackOpinion;
	}

	public void setFeedbackOpinion(String feedbackOpinion) {
		this.feedbackOpinion = feedbackOpinion;
	}

	public String getStatusVal() {
		return statusVal;
	}

	public void setStatusVal(String statusVal) {
		this.statusVal = statusVal;
	}

	public String getPartyPersonName() {
		return partyPersonName;
	}

	public void setPartyPersonName(String partyPersonName) {
		this.partyPersonName = partyPersonName;
	}

	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}

	public Date getAcceptEndTime() {
		return acceptEndTime;
	}

	public void setAcceptEndTime(Date acceptEndTime) {
		this.acceptEndTime = acceptEndTime;
	}

	@JsonFormat(pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
	public Date getAcceptStartTime() {
		return acceptStartTime;
	}

	public void setAcceptStartTime(Date acceptStartTime) {
		this.acceptStartTime = acceptStartTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptStartTime1() {
		return acceptStartTime1;
	}

	public void setAcceptStartTime1(Date acceptStartTime1) {
		this.acceptStartTime1 = acceptStartTime1;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptStartTime2() {
		return acceptStartTime2;
	}

	public void setAcceptStartTime2(Date acceptStartTime2) {
		this.acceptStartTime2 = acceptStartTime2;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptStartTime3() {
		return acceptStartTime3;
	}

	public void setAcceptStartTime3(Date acceptStartTime3) {
		this.acceptStartTime3 = acceptStartTime3;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptEndTime1() {
		return acceptEndTime1;
	}

	public void setAcceptEndTime1(Date acceptEndTime1) {
		this.acceptEndTime1 = acceptEndTime1;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptEndTime2() {
		return acceptEndTime2;
	}

	public void setAcceptEndTime2(Date acceptEndTime2) {
		this.acceptEndTime2 = acceptEndTime2;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getAcceptEndTime3() {
		return acceptEndTime3;
	}

	public void setAcceptEndTime3(Date acceptEndTime3) {
		this.acceptEndTime3 = acceptEndTime3;
	}

	public BigDecimal getTaxAgentMoneyPay() {
		return taxAgentMoneyPay;
	}

	public void setTaxAgentMoneyPay(BigDecimal taxAgentMoneyPay) {
		// （A/0.2）*0.16+A；
		taxAgentMoneyPay = taxAgentMoneyPay.divide(BigDecimal.valueOf(0.2d)).multiply(BigDecimal.valueOf(0.16d)).add(taxAgentMoneyPay);
		this.taxAgentMoneyPay = taxAgentMoneyPay;
	}

	public BigDecimal getAgentMoneyPay() {
		return agentMoneyPay;
	}

	public void setAgentMoneyPay(BigDecimal agentMoneyPay) {
		// （A/0.2）*0.03+A
		agentMoneyPay = agentMoneyPay.divide(BigDecimal.valueOf(0.2d)).multiply(BigDecimal.valueOf(0.03d)).add(agentMoneyPay);
		this.agentMoneyPay = agentMoneyPay;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getCustomStartTime() {
		return customStartTime;
	}

	public void setCustomStartTime(Date customStartTime) {
		this.customStartTime = customStartTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getCustomEndTime() {
		return customEndTime;
	}

	public void setCustomEndTime(Date customEndTime) {
		this.customEndTime = customEndTime;
	}

}
