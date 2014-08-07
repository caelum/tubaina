package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class SubsectionTag implements Tag<SubsectionChunk>{

static final String HTML_TAG = "h4";
	
	@Override
	public String parse(SubsectionChunk chunk) {
		int nextSubsection = chunk.getSubsectionNumber();
		int currentChapter = chunk.getCurrentChapter();
		int currentSection = chunk.getCurrentSection();
		ChapterBuilder.getChaptersCount();
		String sanitized = new HtmlSanitizer().sanitize(chunk.getTitle());
		String subsectionTitle = String.format("<" + HTML_TAG + " class='subsection'>%d.%d.%d - %s</" + HTML_TAG + ">", currentChapter, currentSection, nextSubsection, sanitized);
		
		return subsectionTitle + "\n" + chunk.getContent();
	}

}
