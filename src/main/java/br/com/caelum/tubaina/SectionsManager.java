package br.com.caelum.tubaina;

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

}
