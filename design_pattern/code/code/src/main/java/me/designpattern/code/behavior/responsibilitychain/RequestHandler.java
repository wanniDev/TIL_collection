package me.designpattern.code.behavior.responsibilitychain;

public class RequestHandler {
	private Request request;

	public RequestHandler(Request request) {
		this.request = request;
	}

	public void handle(Request request) {
		System.out.println("인증이 되었나?");
		System.out.println("이 핸들러를 사용할 수 있는 유저인가?");
		System.out.println(request.getBody());
	}
}
