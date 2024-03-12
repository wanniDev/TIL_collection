package me.designpattern.code.behavior.responsibilitychain;

public class Client {
	private final RequestHandler requestHandler;

	public Client(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	public void doWork() {
		Request request = new Request("request payload");
		requestHandler.handle(request);
	}

	public static void main(String[] args) {
		RequestHandler chain = new AuthRequestHandler(new LoggingRequestHandler(new PrintRequestHandler(null)));
		Client client = new Client(chain);
		client.doWork();
	}
}
