package com.law.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class KeyValue {

	private String donetiem;
	private BigDecimal money;
	private BigInteger orderNum;

	public KeyValue() {
		super();
	}

	public KeyValue(String donetiem, BigDecimal money, BigInteger orderNum) {
		super();
		this.donetiem = donetiem;
		this.money = money;
		this.orderNum = orderNum;
	}

	public BigInteger getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(BigInteger orderNum) {
		this.orderNum = orderNum;
	}

	public String getDonetiem() {
		return donetiem;
	}

	public void setDonetiem(String donetiem) {
		this.donetiem = donetiem;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "KeyValue [donetiem=" + donetiem + ", money=" + money + ", orderNum=" + orderNum + "]";
	}

}
