package me.designpattern.code.composition.decorator;

public class SpamFilteringCommentDecorator extends CommentDecorator {

	public SpamFilteringCommentDecorator(CommentService commentService) {
		super(commentService);
	}

	@Override
	public void addComment(String comment) {
		StringBuilder sb = new StringBuilder(comment);
		sb.insert(0, "add filter spam");
		if (isNotSpam(comment))
			super.addComment(sb.toString());
	}

	private boolean isNotSpam(String comment) {
		return true;
	}
}
