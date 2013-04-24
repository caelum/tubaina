package br.com.caelum.tubaina.parser.latex;

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
		return "\n\n" + parser.parse(chunk.getContent());
	}

}
