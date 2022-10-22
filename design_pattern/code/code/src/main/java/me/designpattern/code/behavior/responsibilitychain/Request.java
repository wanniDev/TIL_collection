package me.designpattern.code.behavior.responsibilitychain;

public class Request {
	private String body;

	public Request() {}

	public Request(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
