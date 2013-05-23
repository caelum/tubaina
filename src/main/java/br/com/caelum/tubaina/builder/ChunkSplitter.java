package br.com.caelum.tubaina.builder;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.resources.Resource;

public class ChunkSplitter {

	private List<Resource> resources;
	private String replacerType;
	private final SectionsManager sectionsManager;

	public ChunkSplitter(List<Resource> resources, String replacerType, SectionsManager sectionsManager) {
		this.resources = resources;
		this.replacerType = replacerType;
		this.sectionsManager = sectionsManager;
	}
	
	public ChunkSplitter(List<Resource> resources, String replacerType) {
		this(resources, replacerType, new SectionsManager());
	}

	public List<Chunk> splitChunks(String text) {
		ChunksMaker maker = new ChunksMakerBuilder(resources, sectionsManager).build(replacerType);
		return maker.make(text);
	}
}
