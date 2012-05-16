package br.com.caelum.tubaina;

import java.util.ArrayList;
import java.util.List;

public class Book {

	private List<BookPart> book;

	private String name;

	private final boolean showNotes;

	public Book(String name, List<BookPart> bookParts, boolean showNotes) {
	    this.book = bookParts;
		this.name = name;
		this.showNotes = showNotes;
	}

	public List<Chapter> getChapters() {
	    ArrayList<Chapter> allChapters = new ArrayList<Chapter>();
		for (BookPart part : book) {
		    allChapters.addAll(part.getChapters());
        }
		return allChapters;
	}
	
	public List<BookPart> getParts() {
        return book;
    }

	public String getName() {
		return name;
	}

	public boolean isInstructorBook() {
		return showNotes;
	}
}
