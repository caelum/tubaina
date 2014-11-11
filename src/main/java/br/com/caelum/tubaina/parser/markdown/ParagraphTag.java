package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;

import com.google.inject.Inject;

public class ParagraphTag implements Tag<ParagraphChunk> {

	private final Parser parser;

	@Inject
	public ParagraphTag(Parser parser) {
		this.parser = parser;
	}
	
	@Override
	public String parse(ParagraphChunk chunk) {
		return parser.parse(chunk.getContent()) + "\n\n";
	}

}
