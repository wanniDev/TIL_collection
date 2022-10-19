package me.designpattern.code.composition.decorator;

public class SpamFilteringCommentDecorator extends CommentDecorator {

	public SpamFilteringCommentDecorator(CommentService commentService) {
		super(commentService);
	}

	@Override
	public void addComment(String comment) {
		if (isNotSpam(comment))
			super.addComment(comment);
	}

	private boolean isNotSpam(String comment) {
		return true;
	}
}
