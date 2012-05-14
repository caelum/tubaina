package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.resources.Resource;

public class BoxReplacer extends AbstractReplacer {

	private List<Resource> resources;
	
	public BoxReplacer(List<Resource> resources) {
		super("box");
		this.resources = resources;
	}

	@Override
	public Chunk createChunk(String options, String content) {
		String title = options.trim();
		List<Chunk> body = new ChunkSplitter(resources, ReplacerType.BOX).splitChunks(content);
		return new BoxChunk(title, body);
	}

}
