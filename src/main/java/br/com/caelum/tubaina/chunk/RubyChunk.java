package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class RubyChunk implements Chunk {

	private final String content;
	private final String options;

	public RubyChunk(String content, String options) {
		this.content = content;
		this.options = options;
	}

	public String asString(Parser p) {
		return p.parseRuby(content, options);
	}

}
