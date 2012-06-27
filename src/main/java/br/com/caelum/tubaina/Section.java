package br.com.caelum.tubaina;

import java.util.List;

import br.com.caelum.tubaina.util.TitleSlug;

public class Section {

	private static int COUNT = 1;
	
	private List<Chunk> chunks;

	private String title;
	
	private int sectionNumber;

    private String titleSlug;

	public Section(String title, List<Chunk> chunks) {
		this.title = title;
		this.titleSlug = new TitleSlug(title).toString();
		this.chunks = chunks;
		this.sectionNumber = COUNT;
		COUNT++;
	}

	public String getTitle() {
		return title;
	}
	
	public List<Chunk> getChunks() {
		return chunks;
	}
	
	public static int getSectionsCount() {
		return COUNT;
	}
	
	public int getSectionNumber() {
        return sectionNumber;
    }
	
	public String getTitleSlug() {
        return titleSlug;
    }
	
	public boolean isIntro() {
	    return title == null;
	}
	
}
