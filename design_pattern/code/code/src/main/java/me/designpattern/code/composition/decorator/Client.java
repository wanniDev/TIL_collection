package me.designpattern.code.composition.decorator;

public class Client {
	private final CommentService commentService;

	private boolean enableSpamFilter;

	private boolean enableTrimming;

	public Client(CommentService commentService) {
		this.commentService = commentService;
	}

	private void writeComment(String comment) {
		commentService.addComment(comment);
	}

	public static void main(String[] args) {
		Client client = new Client(new CommentService());
		client.writeComment("릭앤모티 봤냐?");
		client.writeComment("고양이 만세");
	}
}
