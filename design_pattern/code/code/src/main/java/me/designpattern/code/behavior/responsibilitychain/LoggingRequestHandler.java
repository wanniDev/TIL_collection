package me.designpattern.code.behavior.responsibilitychain;

public class LoggingRequestHandler extends RequestHandler {
	@Override
	public void handle(Request request) {
		System.out.println("로깅");
	}
}
