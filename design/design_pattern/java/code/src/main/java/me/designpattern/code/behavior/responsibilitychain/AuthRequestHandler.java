package me.designpattern.code.behavior.responsibilitychain;

public class AuthRequestHandler extends RequestHandler {
	public AuthRequestHandler(RequestHandler nextHandler) {
		super(nextHandler);
	}

	@Override
	public void handle(Request request) {
		System.out.println("인증 여부 확인");
		super.handle(request);
	}
}
