package me.designpattern.code.composition.decorator;

public class Client {
	private final CommentService commentService;

	public Client(CommentService commentService) {
		this.commentService = commentService;
	}

	private void writeComment(String comment) {
		commentService.addComment(comment);
	}

	public static void main(String[] args) {
		CommentService defaultCommentService = new DefaultCommentService();
		defaultCommentService = new TrimmingCommentDecorator(defaultCommentService);
		defaultCommentService = new SpamFilteringCommentDecorator(defaultCommentService);
		Client client = new Client(defaultCommentService);

		client.writeComment("릭앤모티 봤냐?");
		client.writeComment("고양이 만세");
	}
}
