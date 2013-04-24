package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ParagraphTagTemplate;
import br.com.caelum.tubaina.util.Sanitizer;

import com.google.inject.Inject;

public class ParagraphTag implements Tag<ParagraphChunk> {

	private ParagraphTagTemplate template;

	@Inject
	public ParagraphTag(Sanitizer sanitizer, Parser parser) {
		this.template = new ParagraphTagTemplate(sanitizer, parser);
	}
	
	@Override
	public String parse(ParagraphChunk chunk) {
		return template.parse(chunk);
	}

}
