package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class TodoChunk extends AbstractChunk<TodoChunk> {

	private String content;

	public TodoChunk(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
