package me.designpattern.code.behavior.iterator;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class RecentPostIterator implements Iterator<Post> {

	private Iterator<Post> internalIterator;

	public RecentPostIterator(Board board) {
		List<Post> posts = board.getPosts();
		Collections.sort(posts, (o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
		this.internalIterator = posts.iterator();
	}

	@Override
	public boolean hasNext() {
		return internalIterator.hasNext();
	}

	@Override
	public Post next() {
		return internalIterator.next();
	}
}
