package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.resources.Resource;

public class SubsectionReplacer extends AbstractReplacer {

	private final SectionsManager sectionsManager;
	private final List<Resource> resources;

	public SubsectionReplacer(SectionsManager sectionsManager, List<Resource> resources) {
		super("subsection");
		this.sectionsManager = sectionsManager;
		this.resources = resources;
	}

	@Override
	protected Chunk createChunk(String options, String content) {
		int subsectionNumber = sectionsManager.nextSubsection();
		int currentChapter = sectionsManager.getCurrentChapter();
		int currentSection = sectionsManager.getCurrentSection();
		List<Chunk> body = new ChunkSplitter(resources, "subsection").splitChunks(content);
		return new SubsectionChunk(options, body, subsectionNumber, currentChapter, currentSection);
	}

}
