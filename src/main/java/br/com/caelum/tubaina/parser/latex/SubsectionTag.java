package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;

import com.google.inject.Inject;

public class SubsectionTag implements Tag<SubsectionChunk> {
	
	private final SectionsManager subsectionManager;
	private final Parser parser;

	@Inject
	public SubsectionTag(SectionsManager subsectionManager, Parser parser) {
		this.subsectionManager = subsectionManager;
		this.parser = parser;
	}

	@Override
	public String parse(SubsectionChunk chunk) {
		subsectionManager.nextSubsection();
		return "\\subsection{" + chunk.getTitle() + "}\n" + chunk.getContent();
	}

}
