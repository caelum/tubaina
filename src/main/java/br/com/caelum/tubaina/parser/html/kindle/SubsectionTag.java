package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.parser.Tag;

public class SubsectionTag implements Tag<SubsectionChunk> {
	
	@Override
	public String parse(SubsectionChunk chunk) {
		int nextSubsection = chunk.getSubsectionNumber();
		int currentChapter = chunk.getCurrentChapter();
		int currentSection = chunk.getCurrentSection();
		ChapterBuilder.getChaptersCount();
		return String.format("<h3>%d.%d.%d - %s</h3>", currentChapter, currentSection, nextSubsection, chunk.getTitle());
	}

}
