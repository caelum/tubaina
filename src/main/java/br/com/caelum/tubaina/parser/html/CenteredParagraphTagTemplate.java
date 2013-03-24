package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;
import br.com.caelum.tubaina.parser.Tag;

public class CenteredParagraphTagTemplate implements Tag<CenteredParagraphChunk> {

	@Override
	public String parse(CenteredParagraphChunk chunk) {
		return "<p class=\"center\">" + chunk.getContent() + "</p>";
	}

}
