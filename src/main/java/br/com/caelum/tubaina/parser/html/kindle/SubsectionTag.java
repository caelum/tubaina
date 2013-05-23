package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.parser.Tag;

import com.google.inject.Inject;

public class SubsectionTag implements Tag<SubsectionChunk> {
	
	private final SectionsManager sectionsManager;

	@Inject
	public SubsectionTag(SectionsManager sectionsManager) {
		this.sectionsManager = sectionsManager;
	}

	@Override
	public String parse(SubsectionChunk chunk) {
		sectionsManager.nextSubsection();
		ChapterBuilder.getChaptersCount();
		return "\\subsection{" + chunk.getTitle() + "}\n";
	}

}
