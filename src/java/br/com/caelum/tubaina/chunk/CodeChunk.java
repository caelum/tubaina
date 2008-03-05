package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class CodeChunk implements Chunk {

	private String content;
	private final String options;

	public CodeChunk(String content, String options) {
		this.content = content;
		this.options = options;
	}

	public String getContent(Parser p) {
		return p.parseCode(this.content, this.options);
	}

}
