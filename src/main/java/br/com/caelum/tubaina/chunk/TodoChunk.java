package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class TodoChunk implements Chunk {

	private String content;

	public TodoChunk(String content) {
		this.content = content;
	}

	public String asString(Parser p) {
		return p.parseTodo(this.content);
	}

}
