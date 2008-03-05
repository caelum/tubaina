package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
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
		if (options == null || options.trim().length() == 0) {
			throw new TubainaException("Boxes must have a non-empty title");
		}
		String title = options.trim();
		List<Chunk> body = new ChunkSplitter(resources, "box").splitChunks(content);
		return new BoxChunk(title, body);
	}

}
