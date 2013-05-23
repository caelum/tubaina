package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class SubsectionChunk extends AbstractChunk<SubsectionChunk> {

	private final String title;
	private final int subsectionNumber;
	private final int currentChapter;
	private final int currentSection;

	public SubsectionChunk(String title, int subsectionNumber,
			int currentChapter, int currentSection) {
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
