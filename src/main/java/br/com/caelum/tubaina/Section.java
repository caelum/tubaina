package br.com.caelum.tubaina;

import java.util.List;

public class Section {

	private static int COUNT = 1;
	
	private List<Chunk> chunks;

	private String title;

	public Section(String title, List<Chunk> chunks) {
		this.title = title;
		this.chunks = chunks;
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
}
