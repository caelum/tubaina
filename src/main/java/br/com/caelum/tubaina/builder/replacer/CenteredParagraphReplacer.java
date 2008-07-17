package br.com.caelum.tubaina.builder.replacer;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;

public class CenteredParagraphReplacer extends AbstractReplacer {
	public CenteredParagraphReplacer() {
		super("center");
	}
	@Override
	protected Chunk createChunk(String options, String content) {
		return new CenteredParagraphChunk(content);
	}

}
