package br.com.caelum.tubaina;

import java.util.List;

public class Book {

	private List<Chapter> chapters;

	private String name;

	public Book(String name, List<Chapter> chapters) {
		this.chapters = chapters;
		this.name = name;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public String getName() {
		return name;
	}
}
