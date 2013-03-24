package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.TodoChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TodoTagTemplate implements Tag<TodoChunk> {

	private TodoTagTemplate template = new TodoTagTemplate();
	
	@Override
	public String parse(TodoChunk chunk) {
		return template.parse(chunk);
	}

}
