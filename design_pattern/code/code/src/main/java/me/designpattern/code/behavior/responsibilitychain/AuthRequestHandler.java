package me.designpattern.code.behavior.responsibilitychain;

public class AuthRequestHandler extends RequestHandler {
	@Override
	public void handle(Request request) {
		System.out.println("인증");
	}
}
