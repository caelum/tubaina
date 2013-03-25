package br.com.caelum.tubaina.parser;

import br.com.caelum.tubaina.chunk.IntroductionChunk;

public class IntroductionTag implements Tag<IntroductionChunk> {

	@Override
	public String parse(IntroductionChunk chunk) {
		return chunk.getContent();
	}

}
