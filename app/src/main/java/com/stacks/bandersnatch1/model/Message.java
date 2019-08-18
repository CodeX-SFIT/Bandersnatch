package com.stacks.bandersnatch1.model;

import java.util.Date;

public class Message {
	Date time;
	Boolean isBot;
	String message;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Boolean getBot() {
		return isBot;
	}

	public void setBot(Boolean bot) {
		isBot = bot;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
