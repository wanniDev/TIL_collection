package me.designpattern.code.behavior.iterator;

import java.util.Iterator;
import java.util.List;

public class Board {
	List<Post> posts;

	public List<Post> getPosts() {
		return posts;
	}

	public void addPost(String content) {
		this.posts.add(new Post(content));
	}

	public Iterator<Post> getRecentPostIterator() {
		return new RecentPostIterator(this);
	}
}
