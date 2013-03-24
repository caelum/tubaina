package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ParagraphTag implements Tag<ParagraphChunk> {

	@Override
	public String parse(ParagraphChunk chunk) {
		return "\n\n" + chunk.getContent();
	}

}
