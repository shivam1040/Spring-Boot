package com.sts;
//this class used for sending message to view, be it of any type, error, sucess etc.
public class Message {
	private String content, type;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Message(String content, String type) {
		super();
		this.content = content;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + ", type=" + type + "]";
	}
	
	
}
