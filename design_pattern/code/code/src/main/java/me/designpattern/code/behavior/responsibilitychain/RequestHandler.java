package me.designpattern.code.behavior.responsibilitychain;

public class RequestHandler {

	public void handle(Request request) {
		System.out.println(request.getBody());
	}
}
