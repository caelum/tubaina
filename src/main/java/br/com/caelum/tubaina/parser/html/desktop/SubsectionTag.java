package br.com.caelum.tubaina.parser.html.desktop;

import com.google.inject.Inject;

import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class SubsectionTag implements Tag<SubsectionChunk>{

static final String HTML_TAG = "h4";

private SectionsManager subsectionManager;

	@Inject
	public SubsectionTag(SectionsManager subsectionManager){
		this.subsectionManager = subsectionManager;	
	}
	
	@Override
	public String parse(SubsectionChunk chunk) {
		int nextSubsection = chunk.getSubsectionNumber();
		int currentChapter = chunk.getCurrentChapter();
		int currentSection = chunk.getCurrentSection();
		ChapterBuilder.getChaptersCount();
		String sanitized = new HtmlSanitizer().sanitize(chunk.getTitle());
		String subsectionTitle = String.format("<" + HTML_TAG + " class='subsection'>%d.%d.%d - %s</" + HTML_TAG + ">", currentChapter, currentSection, nextSubsection, sanitized);
		
		subsectionManager.nextSubsection();
		
		return subsectionTitle + "\n" + chunk.getContent();
	}

}
