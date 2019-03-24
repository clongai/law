package com.law.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_quote")
public class OrderQuote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "order_id")
	private Integer orderId;
	@Column(name = "quote_a")
	private Double quoteA;
	@Column(name = "quote_b")
	private Double quoteB;
	@Column(name = "quote_c")
	private Double quoteC;
	private Integer type;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getQuoteA() {
		return quoteA;
	}

	public void setQuoteA(Double quoteA) {
		this.quoteA = quoteA;
	}

	public Double getQuoteB() {
		return quoteB;
	}

	public void setQuoteB(Double quoteB) {
		this.quoteB = quoteB;
	}

	public Double getQuoteC() {
		return quoteC;
	}

	public void setQuoteC(Double quoteC) {
		this.quoteC = quoteC;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderQuote [id=" + id + ", orderId=" + orderId + ", quoteA=" + quoteA + ", quoteB=" + quoteB + ", quoteC=" + quoteC + ", type=" + type + ", status=" + status + "]";
	}

}
