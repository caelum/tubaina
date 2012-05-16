package br.com.caelum.tubaina;

import java.util.ArrayList;
import java.util.List;

public class Book {

	private List<BookPart> bookParts;

	private String name;

	private final boolean showNotes;

	public Book(String name, List<Chapter> chapters, boolean showNotes) {
	    this.bookParts = new ArrayList<BookPart>();
	    bookParts.add(new BookPart(chapters));
		this.name = name;
		this.showNotes = showNotes;
	}

	public List<Chapter> getChapters() {
	    ArrayList<Chapter> allChapters = new ArrayList<Chapter>();
		for (BookPart part : bookParts) {
		    allChapters.addAll(part.getChapters());
        }
		return allChapters;
	}

	public String getName() {
		return name;
	}

	public boolean isInstructorBook() {
		return showNotes;
	}
}
