package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ParagraphTagTemplate implements Tag<ParagraphChunk> {

	@Override
	public String parse(ParagraphChunk chunk) {
		return "<p>" + chunk.getContent() + "</p>";
	}

}
