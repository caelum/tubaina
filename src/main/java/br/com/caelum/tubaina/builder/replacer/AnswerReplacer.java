package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.resources.AnswerResource;
import br.com.caelum.tubaina.resources.Resource;

public class AnswerReplacer extends AbstractReplacer {

	private List<Resource> resources;

	public AnswerReplacer(List<Resource> resources) {
		super("answer");
		this.resources = resources;
	}

	@Override
	public Chunk createChunk(String options, String content) {
		AnswerChunk chunk = new AnswerChunk(new ChunkSplitter(resources, "answer").splitChunks(content));
		resources.add(new AnswerResource(chunk));
		return chunk;
	}

}
