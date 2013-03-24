package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ParagraphTagTemplate;

public class ParagraphTag implements Tag<ParagraphChunk> {

	private ParagraphTagTemplate template = new ParagraphTagTemplate();
	
	@Override
	public String parse(ParagraphChunk chunk) {
		return template.parse(chunk);
	}

}
