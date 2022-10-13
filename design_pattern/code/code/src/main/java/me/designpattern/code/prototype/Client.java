package me.designpattern.code.prototype;

public class Client {
	public static void main(String[] args) throws CloneNotSupportedException {
		GithubRepository repository = new GithubRepository();
		repository.setUser("wanniDev");
		repository.setName("designPattern");

		GithubIssue githubIssue = new GithubIssue(repository);
		githubIssue.setId(1);
		githubIssue.setTitle("issue title");

		String url = githubIssue.getUrl();
		System.out.println(url);
		GithubIssue githubIssue2 = (GithubIssue)githubIssue.clone();
		githubIssue.setId(2);
		githubIssue.setTitle("issue title2");
	}
}
