package com.law.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Auther: nonghz
 * @Date: 2018/10/29 12:01
 * @Description:
 */
@Entity
@Table(name = "law_order_his", schema = "law", catalog = "")
public class LawOrderHis {
    private String acceptAddress;
    private String acceptContracts;
    private Timestamp acceptDate;
    private String acceptPhone;
    private String acceptRemark;
    private String appeal;
    private String doneCode;
    private Timestamp doneDate;
    private String endTime;
    private String files;
    private String happen;
    private BigDecimal involvingMoney;
    private String openId;
    private int orderId;
    private String photos;
    private String place;
    private String possessions;
    private String refuseReason;
    private String startTime;
    private String status;
    private String videos;
    private Integer serviceId;

    @Basic
    @Column(name = "accept_address", nullable = true, length = 255)
    public String getAcceptAddress() {
        return acceptAddress;
    }

    public void setAcceptAddress(String acceptAddress) {
        this.acceptAddress = acceptAddress;
    }

    @Basic
    @Column(name = "accept_contracts", nullable = true, length = 255)
    public String getAcceptContracts() {
        return acceptContracts;
    }

    public void setAcceptContracts(String acceptContracts) {
        this.acceptContracts = acceptContracts;
    }

    @Basic
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "accept_date", nullable = true)
    public Timestamp getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Timestamp acceptDate) {
        this.acceptDate = acceptDate;
    }

    @Basic
    @Column(name = "accept_phone", nullable = true, length = 255)
    public String getAcceptPhone() {
        return acceptPhone;
    }

    public void setAcceptPhone(String acceptPhone) {
        this.acceptPhone = acceptPhone;
    }

    @Basic
    @Column(name = "accept_remark", nullable = true, length = 4000)
    public String getAcceptRemark() {
        return acceptRemark;
    }

    public void setAcceptRemark(String acceptRemark) {
        this.acceptRemark = acceptRemark;
    }

    @Basic
    @Column(name = "appeal", nullable = true, length = 2000)
    public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal;
    }

    @Basic
    @Column(name = "done_code", nullable = true, length = 50)
    public String getDoneCode() {
        return doneCode;
    }

    public void setDoneCode(String doneCode) {
        this.doneCode = doneCode;
    }

    @Basic
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "done_date", nullable = true)
    public Timestamp getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Timestamp doneDate) {
        this.doneDate = doneDate;
    }

    @Basic
    @Column(name = "end_time", nullable = true, length = 20)
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "files", nullable = true, length = 255)
    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    @Basic
    @Column(name = "happen", nullable = true, length = 4000)
    public String getHappen() {
        return happen;
    }

    public void setHappen(String happen) {
        this.happen = happen;
    }

    @Basic
    @Column(name = "involving_money", nullable = true)
    public BigDecimal getInvolvingMoney() {
        return involvingMoney;
    }

    public void setInvolvingMoney(BigDecimal involvingMoney) {
        this.involvingMoney = involvingMoney;
    }

    @Basic
    @Column(name = "open_id", nullable = true, length = 255)
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Id
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "photos", nullable = true, length = 255)
    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Basic
    @Column(name = "place", nullable = true, length = 255)
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "possessions", nullable = true, length = 4000)
    public String getPossessions() {
        return possessions;
    }

    public void setPossessions(String possessions) {
        this.possessions = possessions;
    }

    @Basic
    @Column(name = "refuse_reason", nullable = true, length = 255)
    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    @Basic
    @Column(name = "start_time", nullable = true, length = 20)
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 50)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "videos", nullable = true, length = 255)
    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    @Basic
    @Column(name = "service_id", nullable = true)
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, doneCode, doneDate, startTime, endTime, place, happen, involvingMoney, possessions, appeal, photos, videos, files, openId, status, acceptDate, acceptContracts, acceptPhone, acceptAddress, acceptRemark, refuseReason, serviceId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LawOrderHis lawOrder = (LawOrderHis) o;
        return orderId == lawOrder.orderId &&
                Objects.equals(doneCode, lawOrder.doneCode) &&
                Objects.equals(doneDate, lawOrder.doneDate) &&
                Objects.equals(startTime, lawOrder.startTime) &&
                Objects.equals(endTime, lawOrder.endTime) &&
                Objects.equals(place, lawOrder.place) &&
                Objects.equals(happen, lawOrder.happen) &&
                Objects.equals(involvingMoney, lawOrder.involvingMoney) &&
                Objects.equals(possessions, lawOrder.possessions) &&
                Objects.equals(appeal, lawOrder.appeal) &&
                Objects.equals(photos, lawOrder.photos) &&
                Objects.equals(videos, lawOrder.videos) &&
                Objects.equals(files, lawOrder.files) &&
                Objects.equals(openId, lawOrder.openId) &&
                Objects.equals(status, lawOrder.status) &&
                Objects.equals(acceptDate, lawOrder.acceptDate) &&
                Objects.equals(acceptContracts, lawOrder.acceptContracts) &&
                Objects.equals(acceptPhone, lawOrder.acceptPhone) &&
                Objects.equals(acceptAddress, lawOrder.acceptAddress) &&
                Objects.equals(acceptRemark, lawOrder.acceptRemark) &&
                Objects.equals(refuseReason, lawOrder.refuseReason) &&
                Objects.equals(serviceId, lawOrder.serviceId);
    }
}
