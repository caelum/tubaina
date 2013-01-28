package br.com.caelum.tubaina;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.tubaina.resources.Resource;

public class Book {

	private List<BookPart> parts;

	private String name;

	private final boolean showNotes;

    private final List<Chapter> introductionChapters;
	
	public Book(String name, List<BookPart> bookParts, boolean showNotes, List<Chapter> introductionChapters) {
	    this.parts = bookParts;
		this.name = name;
		this.showNotes = showNotes;
        this.introductionChapters = introductionChapters;
	}

	public List<Chapter> getChapters() {
	    ArrayList<Chapter> allChapters = new ArrayList<Chapter>();
		for (BookPart part : parts) {
		    allChapters.addAll(part.getChapters());
        }
		return allChapters;
	}
	
	public List<BookPart> getParts() {
        return parts;
    }

	public String getName() {
		return name;
	}

	public boolean isInstructorBook() {
		return showNotes;
	}
	
	public List<Chapter> getIntroductionChapters() {
        return introductionChapters;
    }

    public List<Resource> getResources() {
        List<Resource> resources = new ArrayList<Resource>();
        for (Chapter c : this.getChapters()) {
            resources.addAll(c.getResources());
        }
        for (Chapter c : this.getIntroductionChapters()) {
            resources.addAll(c.getResources());
        }
        for (BookPart bp : this.getParts()) {
            resources.addAll(bp.getResources());
        }
        return resources;
    }
	
}
