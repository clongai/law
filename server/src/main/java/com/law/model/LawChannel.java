package com.law.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: nonghz
 * @Date: 2018/11/6 15:30
 * @Description:
 */
@Entity
@Table(name = "law_channel", schema = "law", catalog = "")
public class LawChannel {
    private int channelId;
    private String channelCode;
    private String channelName;
    private String channlAccount;
    private int channelManagerId;
    private String status;
    private Date createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Basic
    @Column(name = "channel_code")
    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Basic
    @Column(name = "channel_name")
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Basic
    @Column(name = "channel_account")
    public String getChannlAccount() {
        return channlAccount;
    }

    public void setChannlAccount(String channlAccount) {
        this.channlAccount = channlAccount;
    }

    @Basic
    @Column(name = "channel_manager_id")
    public int getChannelManagerId() {
        return channelManagerId;
    }

    public void setChannelManagerId(int channelManagerId) {
        this.channelManagerId = channelManagerId;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
