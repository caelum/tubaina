package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;
import br.com.caelum.tubaina.parser.Tag;

public class CenteredParagraphTag implements Tag<CenteredParagraphChunk> {

	@Override
	public String parse(CenteredParagraphChunk chunk) {
		return "\n\n" + chunk.getContent() + "\n\n";
	}

}
