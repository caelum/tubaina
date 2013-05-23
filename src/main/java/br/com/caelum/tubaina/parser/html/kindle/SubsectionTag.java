package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.parser.Tag;

public class SubsectionTag implements Tag<SubsectionChunk> {
	
	static final String HTML_TAG = "h4";

	@Override
	public String parse(SubsectionChunk chunk) {
		int nextSubsection = chunk.getSubsectionNumber();
		int currentChapter = chunk.getCurrentChapter();
		int currentSection = chunk.getCurrentSection();
		ChapterBuilder.getChaptersCount();
		return String.format("<" + HTML_TAG + ">%d.%d.%d - %s</" + HTML_TAG + ">", currentChapter, currentSection, nextSubsection, chunk.getTitle());
	}

}
