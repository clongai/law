package com.law.model;

import java.util.Date;

public class PromoterParam {

	private String name;
	private String superiorName;
	private Date startTime;
	private Date endTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuperiorName() {
		return superiorName;
	}

	public void setSuperiorName(String superiorName) {
		this.superiorName = superiorName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "PromoterParam [name=" + name + ", superiorName=" + superiorName + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
