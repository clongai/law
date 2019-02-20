package com.law.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Auther: nonghz
 * @Date: 2018/10/25 11:01
 * @Description:
 */
@Entity
@Table(name = "law_service", schema = "law", catalog = "")
public class LawServiceEntity {
    private int serviceId;
    private String serviceGroup;
    private Integer serviceFee;
    private String serviceProject;
    private String serviceDetail;
    private int serviceLevel;
    private String status;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "service_group", nullable = true, length = 50)
    public String getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(String serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    @Basic
    @Column(name = "service_fee", nullable = true)
    public Integer getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Basic
    @Column(name = "service_project", nullable = true, length = 20)
    public String getServiceProject() {
        return serviceProject;
    }

    public void setServiceProject(String serviceProject) {
        this.serviceProject = serviceProject;
    }

    @Basic
    @Column(name = "service_detail", nullable = true, length = 500)
    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    @Basic
    @Column(name = "service_level", nullable = true, length = 500)
    public int getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(int serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, serviceGroup, serviceFee, serviceProject, serviceDetail, serviceLevel, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LawServiceEntity lawService = (LawServiceEntity) o;
        return serviceId == lawService.serviceId &&
                Objects.equals(serviceId, lawService.serviceId) &&
                Objects.equals(serviceGroup, lawService.serviceGroup) &&
                Objects.equals(serviceFee, lawService.serviceFee) &&
                Objects.equals(serviceProject, lawService.serviceProject) &&
                Objects.equals(serviceDetail, lawService.serviceDetail) &&
                Objects.equals(serviceLevel, lawService.serviceLevel) &&
                Objects.equals(status, lawService.status);
    }

}
