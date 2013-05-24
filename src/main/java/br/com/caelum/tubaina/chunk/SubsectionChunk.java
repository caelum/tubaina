package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class SubsectionChunk extends CompositeChunk<SubsectionChunk> {

	private final String title;
	private final int subsectionNumber;
	private final int currentChapter;
	private final int currentSection;

	public SubsectionChunk(String title, List<Chunk> body, int subsectionNumber,
			int currentChapter, int currentSection) {
		super(body);
		this.title = title;
		this.subsectionNumber = subsectionNumber;
		this.currentChapter = currentChapter;
		this.currentSection = currentSection;
	}

	public String getTitle() {
		return title;
	}

	public int getCurrentChapter() {
		return currentChapter;
	}

	public int getCurrentSection() {
		return currentSection;
	}

	public int getSubsectionNumber() {
		return subsectionNumber;
	}
	
}
