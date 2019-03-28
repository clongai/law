package com.law.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "promoter_report")
public class PromoterReport {

	public static final String INCOME = "income";
	public static final String NAME = "promoterName";
	public static final String SUPERIOR_NAME = "superiorName";
	public static final String CREATE_DATE = "createTime";

	@Id
	@Column(name = "promoter_id")
	private Integer promoterId;
	@Column(name = "parent_promoter_id")
	private Integer parentPromoterId;
	@Column(name = "promoter_tel")
	private String promoterTel;
	@Column(name = "promoter_name")
	private String promoterName;
	@Column(name = "superior_Name")
	private String superiorName;
	@Column(name = "order_num")
	private BigInteger orderNum;
	@Column(name = "create_time")
	private Date createTime;
	private Integer state;
	private BigDecimal income;
	@Column(name = "subordinate_num")
	private Integer subordinateNum;

	public Integer getSubordinateNum() {
		return subordinateNum;
	}

	public void setSubordinateNum(Integer subordinateNum) {
		this.subordinateNum = subordinateNum;
	}

	public Integer getParentPromoterId() {
		return parentPromoterId;
	}

	public void setParentPromoterId(Integer parentPromoterId) {
		this.parentPromoterId = parentPromoterId;
	}

	public String getSuperiorName() {
		return superiorName;
	}

	public void setSuperiorName(String superiorName) {
		this.superiorName = superiorName;
	}

	public Integer getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(Integer promoterId) {
		this.promoterId = promoterId;
	}

	public String getPromoterTel() {
		return promoterTel;
	}

	public void setPromoterTel(String promoterTel) {
		this.promoterTel = promoterTel;
	}

	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	public BigInteger getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(BigInteger orderNum) {
		this.orderNum = orderNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	@Override
	public String toString() {
		return "PromoterReport [promoterId=" + promoterId + ", promoterTel=" + promoterTel + ", promoterName=" + promoterName + ", orderNum=" + orderNum + ", createTime=" + createTime + ", state=" + state + ", income=" + income + "]";
	}

}
