package com.law.model;

import java.math.BigDecimal;

public class PublicityLaw {

	private Integer id;
	private Integer rank;//排序
	private String name;
	private String employeeId;//工号
	private String phone;
	private String degree;//级别
	private String superiorName;//上级姓名
	private Integer subordinateNum;//下级人数
	private Integer orderNum;//成单数
	private BigDecimal income;//收入
	private String complianceRate;//达标率
	private Integer status;//状态

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSuperiorName() {
		return superiorName;
	}

	public void setSuperiorName(String superiorName) {
		this.superiorName = superiorName;
	}

	public Integer getSubordinateNum() {
		return subordinateNum;
	}

	public void setSubordinateNum(Integer subordinateNum) {
		this.subordinateNum = subordinateNum;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getComplianceRate() {
		return complianceRate;
	}

	public void setComplianceRate(String complianceRate) {
		this.complianceRate = complianceRate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PublicityLaw [id=" + id + ", rank=" + rank + ", name=" + name + ", employeeId=" + employeeId + ", phone=" + phone + ", degree=" + degree + ", superiorName=" + superiorName + ", subordinateNum=" + subordinateNum + ", orderNum=" + orderNum + ", income=" + income + ", complianceRate=" + complianceRate + ", status=" + status + "]";
	}

}
