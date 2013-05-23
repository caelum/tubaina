package br.com.caelum.tubaina.builder.replacer;

import java.util.regex.Matcher;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.chunk.SubsectionChunk;

public class SubsectionReplacer extends PatternReplacer {

	private final SectionsManager sectionsManager;

	public SubsectionReplacer(SectionsManager sectionsManager) {
		super("(?s)(?i)\\[subsection (.+?)\\]");
		this.sectionsManager = sectionsManager;
	}

	@Override
	public Chunk createChunk(Matcher matcher) {
		String title = matcher.group(1);
		int subsectionNumber = sectionsManager.nextSubsection();
		int currentChapter = sectionsManager.getCurrentChapter();
		int currentSection = sectionsManager.getCurrentSection();
		return new SubsectionChunk(title, subsectionNumber, currentChapter, currentSection);
	}

}
