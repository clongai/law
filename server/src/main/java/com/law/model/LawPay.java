package com.law.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @Auther: nonghz
 * @Date: 2018/10/26 09:25
 * @Description:
 */
@Entity
@Table(name = "law_pay", schema = "law", catalog = "")
public class LawPay {
    private int payId;
    private String outTradeNo;
    private int orderId;
    private int feeType;
    private int payType;
    private BigDecimal fee;
    private int tradeState;
    private int serviceId;
    private String serviceProject;
    private String doneCode;
    private Date doneDate;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "pay_id", nullable = false)
    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    @Basic
    @Column(name = "out_trade_no")
    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    @Basic
    @Column(name = "service_id")
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "service_project")
    public String getServiceProject() {
        return serviceProject;
    }

    public void setServiceProject(String serviceProject) {
        this.serviceProject = serviceProject;
    }

    @Basic
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "fee_type")
    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    @Basic
    @Column(name = "pay_type")
    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "fee")
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Basic
    @Column(name = "trade_state")
    public int getTradeState() {
        return tradeState;
    }

    public void setTradeState(int tradeState) {
        this.tradeState = tradeState;
    }

    @Basic
    @Column(name = "done_code")
    public String getDoneCode() {
        return doneCode;
    }

    public void setDoneCode(String doneCode) {
        this.doneCode = doneCode;
    }

    @Basic
    @Column(name = "done_date")
    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(payId, orderId, feeType, payType, fee, doneCode, doneDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LawPay lawPay = (LawPay) o;
        return payId == lawPay.payId &&
                Objects.equals(orderId, lawPay.orderId) &&
                Objects.equals(feeType, lawPay.feeType) &&
                Objects.equals(payType, lawPay.payType) &&
                Objects.equals(fee, lawPay.fee) &&
                Objects.equals(doneCode, lawPay.doneCode) &&
                Objects.equals(doneDate, lawPay.doneDate);
    }

}
