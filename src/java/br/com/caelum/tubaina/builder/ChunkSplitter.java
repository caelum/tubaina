package br.com.caelum.tubaina.builder;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.resources.Resource;

public class ChunkSplitter {

	private List<Resource> resources;
	private String replacerType;

	public ChunkSplitter(List<Resource> resources, String replacerType) {
		this.resources = resources;
		this.replacerType = replacerType;
	}

	public List<Chunk> splitChunks(String text) {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(replacerType);
		return maker.make(text);
	}
}
