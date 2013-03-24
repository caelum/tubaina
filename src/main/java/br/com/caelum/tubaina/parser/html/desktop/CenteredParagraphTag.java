package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.CenteredParagraphTagTemplate;

public class CenteredParagraphTag implements Tag<CenteredParagraphChunk> {

	private CenteredParagraphTagTemplate template = new CenteredParagraphTagTemplate();
	
	@Override
	public String parse(CenteredParagraphChunk chunk) {
		return template.parse(chunk);
	}

}
