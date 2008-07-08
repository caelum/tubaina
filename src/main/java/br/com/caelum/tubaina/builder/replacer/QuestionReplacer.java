package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.resources.Resource;

public class QuestionReplacer extends AbstractReplacer {

	private List<Resource> resources;

	public QuestionReplacer(List<Resource> resources) {
		super("question");
		this.resources = resources;
	}

	@Override
	public Chunk createChunk(String options, String content) {
		ChunkSplitter splitter = new ChunkSplitter(resources, "question");
		List<Chunk> chunks = splitter.splitChunks(content);
		return new QuestionChunk(chunks);
	}

}
