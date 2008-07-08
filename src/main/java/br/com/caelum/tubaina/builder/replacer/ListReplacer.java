package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.resources.Resource;

public class ListReplacer extends AbstractReplacer {

	private List<Resource> resources;

	public ListReplacer(List<Resource> resources) {
		super("list");
		this.resources = resources;
	}

	@Override
	public Chunk createChunk(String options, String content) {
		ChunkSplitter splitter = new ChunkSplitter(resources, "list");
		return new ListChunk(options, splitter.splitChunks(content));
	}

}
