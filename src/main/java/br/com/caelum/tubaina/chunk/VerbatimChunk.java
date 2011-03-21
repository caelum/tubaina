package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class VerbatimChunk implements CompositeChunk {

	private final String content;

	public VerbatimChunk(String content) {
		this.content = content;
	}

	public String getContent(Parser p) {
		return content;
	}

}
