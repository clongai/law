package com.law.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: nonghz
 * @Date: 2018/11/6 15:31
 * @Description: 普法宣传员
 */
@Entity
@Table(name = "law_promoter", schema = "law", catalog = "")
public class LawPromoter {
	private int promoterId;
	private int parentPromoterId;
	private String openId;
	private int channelId;
	private String promoterName;
	private String promoterTel;
	private Integer identityId;
	private String bankAccount;
	private Integer promoterLevel;
	private Integer state;
	private String status;
	protected Date createTime;
	private String avatarUrl;

	@Column(name = "avatar_url")
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Id
	@Column(name = "promoter_id")
	public int getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(int promoterId) {
		this.promoterId = promoterId;
	}

	@Basic
	@Column(name = "parent_promoter_id")
	public int getParentPromoterId() {
		return parentPromoterId;
	}

	public void setParentPromoterId(int parentPromoterId) {
		this.parentPromoterId = parentPromoterId;
	}

	@Basic
	@Column(name = "open_id")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Basic
	@Column(name = "channel_id")
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	@Basic
	@Column(name = "promoter_name")
	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	@Basic
	@Column(name = "promoter_tel")
	public String getPromoterTel() {
		return promoterTel;
	}

	public void setPromoterTel(String promoterTel) {
		this.promoterTel = promoterTel;
	}

	@Basic
	@Column(name = "identity_id")
	public Integer getIdentityId() {
		return identityId;
	}

	public void setIdentityId(Integer identityId) {
		this.identityId = identityId;
	}

	@Basic
	@Column(name = "bank_account")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Basic
	@Column(name = "promoter_level")
	public Integer getPromoterLevel() {
		return promoterLevel;
	}

	public void setPromoterLevel(Integer promoterLevel) {
		this.promoterLevel = promoterLevel;
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
	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
