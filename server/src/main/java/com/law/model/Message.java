package com.law.model;

public class Message {

	private String content;
	private String date;
	private boolean self;

	public boolean isSelf() {
		return self;
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + ", date=" + date + ", self=" + self + "]";
	}

}
