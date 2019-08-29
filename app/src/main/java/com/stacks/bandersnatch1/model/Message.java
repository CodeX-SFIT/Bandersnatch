package com.stacks.bandersnatch1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {
	Boolean isBot;
	String message;
	String character_name;
	Integer character_pic = -1;
	Boolean isSeparator = false;
	Integer image = -1;


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

	public Message(){}

	protected Message(Parcel in) {
		byte isBotVal = in.readByte();
		isBot = isBotVal == 0x02 ? null : isBotVal != 0x00;
		message = in.readString();
		character_name = in.readString();
		character_pic = in.readByte() == 0x00 ? null : in.readInt();
		byte isSeparatorVal = in.readByte();
		isSeparator = isSeparatorVal == 0x02 ? null : isSeparatorVal != 0x00;
		image = in.readByte() == 0x00 ? null : in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (isBot == null) {
			dest.writeByte((byte) (0x02));
		} else {
			dest.writeByte((byte) (isBot ? 0x01 : 0x00));
		}
		dest.writeString(message);
		dest.writeString(character_name);
		if (character_pic == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(character_pic);
		}
		if (isSeparator == null) {
			dest.writeByte((byte) (0x02));
		} else {
			dest.writeByte((byte) (isSeparator ? 0x01 : 0x00));
		}
		if (image == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(image);
		}
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
		@Override
		public Message createFromParcel(Parcel in) {
			return new Message(in);
		}

		@Override
		public Message[] newArray(int size) {
			return new Message[size];
		}
	};
}