package br.com.caelum.tubaina;

import java.util.List;

public class Section {

	private static int COUNT = 1;
	
	private List<Chunk> chunks;

	private String title;
	
	private int sectionNumber;

	public Section(String title, List<Chunk> chunks) {
		this.title = title;
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
	
}
