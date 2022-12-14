package me.designpattern.code.composition.decorator;

public class TrimmingCommentDecorator extends CommentDecorator {

	public TrimmingCommentDecorator(CommentService commentService) {
		super(commentService);
	}

	@Override
	public void addComment(String comment) {
		super.addComment(trim(comment));
	}

	private String trim(String comment) {
		return "add trim" + comment.replace("...", "");
	}
}
