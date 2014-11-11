package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.parser.Tag;

import com.google.inject.Inject;

public class SubsectionTag implements Tag<SubsectionChunk> {
	
	private final SectionsManager subsectionManager;

	@Inject
	public SubsectionTag(SectionsManager subsectionManager) {
		this.subsectionManager = subsectionManager;
	}

	@Override
	public String parse(SubsectionChunk chunk) {
		subsectionManager.nextSubsection();
		return "## " + chunk.getTitle() + "\n\n" + chunk.getContent();
	}

}
