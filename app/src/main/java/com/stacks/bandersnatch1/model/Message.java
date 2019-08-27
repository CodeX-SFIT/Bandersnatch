package com.stacks.bandersnatch1.model;

import java.util.Date;

public class Message {
	Date time;
	Boolean isBot;
	String message;
	String character_name;
	Integer character_pic = -1;
	Boolean isSeparator = false;
	Integer image = -1;


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

	public Integer getCharacter_pic() {
		return character_pic;
	}

	public void setCharacter_pic(Integer character_pic) {
		this.character_pic = character_pic;
	}

	public String getCharacter_name() {
		return character_name;
	}

	public void setCharacter_name(String character_name) {
		this.character_name = character_name;
	}

	public Boolean getSeparator() {
		return isSeparator;
	}

	public void setSeparator(Boolean separator) {
		isSeparator = separator;
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}
}
