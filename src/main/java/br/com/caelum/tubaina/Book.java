package br.com.caelum.tubaina;

import java.util.List;

public class Book {

	private List<Chapter> chapters;

	private String name;

	private final boolean showNotes;

	public Book(String name, List<Chapter> chapters, boolean showNotes) {
		this.chapters = chapters;
		this.name = name;
		this.showNotes = showNotes;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public String getName() {
		return name;
	}

	public boolean isInstructorBook() {
		return showNotes;
	}
}
