package br.com.caelum.tubaina.parser.html.kindle;

import com.google.inject.Inject;

import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ParagraphTagTemplate;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class ParagraphTag implements Tag<ParagraphChunk> {

	private ParagraphTagTemplate template;

	@Inject
	public ParagraphTag(HtmlSanitizer sanitizer) {
		this.template = new ParagraphTagTemplate(sanitizer);
	}
	
	@Override
	public String parse(ParagraphChunk chunk) {
		return template.parse(chunk);
	}

}
