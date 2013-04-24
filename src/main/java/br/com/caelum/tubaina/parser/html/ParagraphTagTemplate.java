package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.Sanitizer;

public class ParagraphTagTemplate implements Tag<ParagraphChunk> {

	private final Sanitizer sanitizer;
	private final Parser parser;

	public ParagraphTagTemplate(Sanitizer sanitizer, Parser parser) {
		this.sanitizer = sanitizer;
		this.parser = parser;
	}

	@Override
	public String parse(ParagraphChunk chunk) {
		String content = parser.parse(sanitizer.sanitize(chunk.getContent()));
		if (chunk.isInsideItem())
			return content;
		return "<p>" + content + "</p>";
	}

}
