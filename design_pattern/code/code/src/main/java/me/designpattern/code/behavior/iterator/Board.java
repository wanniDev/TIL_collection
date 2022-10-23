package me.designpattern.code.behavior.iterator;

import java.util.List;

public class Board {
	List<Post> posts;

	public List<Post> getPosts() {
		return posts;
	}

	public void addPost(String content) {
		this.posts.add(new Post(content));
	}
}
