package me.designpattern.code.creational.prototype;

import java.util.Objects;

public class GithubIssue implements Cloneable {
	private int id;
	private String title;

	private String url;

	private final GithubRepository repository;

	public GithubIssue(GithubRepository repository) {
		this.repository = repository;
	}

	public GithubRepository getRepository() {
		return repository;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GithubIssue that = (GithubIssue)o;
		return id == that.id && Objects.equals(title, that.title) && Objects.equals(url, that.url)
			&& Objects.equals(repository, that.repository);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, url, repository);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
