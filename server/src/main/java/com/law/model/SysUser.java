package com.law.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sys_user", schema = "law", catalog = "")
public class SysUser {
	private String phone;
	private int userId;
	private String userName;
	private String userPwd;
	private String photo;
	private int function;
	private int orginazation;
	private String serviceLevel;

	@Basic
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Basic
	@Column(name = "user_pwd")
	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Basic
	@Column(name = "photo")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Basic
	@Column(name = "function")
	public int getFunction() {
		return function;
	}

	public void setFunction(int function) {
		this.function = function;
	}

	@Basic
	@Column(name = "orginazation")
	public int getOrginazation() {
		return orginazation;
	}

	public void setOrginazation(int orginazation) {
		this.orginazation = orginazation;
	}

	@Basic
	@Column(name = "service_level")
	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	@Override
	public int hashCode() {

		return Objects.hash(userId, userName, userPwd, phone, phone, function, orginazation, serviceLevel);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SysUser sysUser = (SysUser) o;
		return userId == sysUser.userId && Objects.equals(userName, sysUser.userName) && Objects.equals(userPwd, sysUser.userPwd) && Objects.equals(phone, sysUser.phone) && Objects.equals(photo, sysUser.photo) && Objects.equals(function, sysUser.function) && Objects.equals(orginazation, sysUser.orginazation) && Objects.equals(serviceLevel, sysUser.serviceLevel);
	}

	@Override
	public String toString() {
		return "SysUser [phone=" + phone + ", userId=" + userId + ", userName=" + userName + ", userPwd=" + userPwd + ", photo=" + photo + ", function=" + function + ", orginazation=" + orginazation + ", serviceLevel=" + serviceLevel + "]";
	}

}
