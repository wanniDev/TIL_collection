package me.designpattern.code.prototype;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.designpattern.code.creational.prototype.GithubIssue;
import me.designpattern.code.creational.prototype.GithubRepository;

class GithubIssueTest {
	@Test
	@DisplayName("clone을 활용한 프로토타입 객체의 경우, equals를 재정의 했을 때는 shallow copy 기반으로 복제를 한다.")
	void testCloneable() throws Exception {
		GithubRepository githubRepository = new GithubRepository();
		GithubIssue githubIssue1 = new GithubIssue(githubRepository);
		GithubIssue clone = (GithubIssue)githubIssue1.clone();

		assertThat(githubIssue1).isNotSameAs(clone);
		assertThat(githubIssue1).isEqualTo(clone);
		assertThat(githubIssue1.getRepository()).isSameAs(clone.getRepository());
	}
}