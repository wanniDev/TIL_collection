package me.designpattern.code.behavior.responsibilitychain;

public class Client {
	public static void main(String[] args) {
		Request request = new Request("request payload");
		RequestHandler requestHandler = new RequestHandler();
		requestHandler.handle(request);
	}
}
