package br.com.caelum.tubaina;

import br.com.caelum.tubaina.builder.ChapterBuilder;

public class SectionsManager {
	
	int currentSubsection = 1;
	int currentSection = 1;

	public int nextSubsection() {
		return currentSubsection++;
	}
	
	public void nextSection() {
		currentSubsection = 1;
		currentSection++;
	}
	
	public void nextChapter() {
		currentSubsection = 1;
		currentSection = 1;
	}
	
	public int getCurrentSection() {
		return currentSection;
	}
	
	public int getCurrentSubsection() {
		return currentSubsection;
	}
	
	public int getCurrentChapter() {
		return ChapterBuilder.getChaptersCount();
	}
	
}
