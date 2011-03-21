package br.com.caelum.tubaina.builder.replacer;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.VerbatimChunk;

public class VerbatimReplacer extends AbstractReplacer {

	
	
	public VerbatimReplacer() {
		super("verbatim");
	}

	@Override
	protected Chunk createChunk(String options, String content) {
		return new VerbatimChunk(content);
	}


}
