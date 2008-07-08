package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class JavaChunk implements Chunk {

	private String content;

	private final String options;

	public JavaChunk(String options, String content) {
		this.content = content;
		this.options = options;
	}

	public String getContent(Parser p) {
		return p.parseJava(content, options);
	}

}
