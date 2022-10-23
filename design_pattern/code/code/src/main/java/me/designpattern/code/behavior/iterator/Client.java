package me.designpattern.code.behavior.iterator;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Client {
	public static void main(String[] args) {
		Board board = new Board();
		board.addPost("디자인 패턴");
		board.addPost("디자인 패턴 스터디 그룹");

		// TODO 들어간 순서대로 순회하기
		List<Post> posts = board.getPosts();
		Iterator<Post> iterator = posts.iterator();
		
		for (Post post : posts) {
			System.out.println(post.getTitle());
		}

		// TODO 가장 최신 글 먼저 순회하기
		Collections.sort(posts, (o1, o2) -> );
	}
}
