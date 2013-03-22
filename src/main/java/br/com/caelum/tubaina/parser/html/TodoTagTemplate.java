package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class TodoTagTemplate implements Tag {

	private TodoTagTemplate template = new TodoTagTemplate();
	
	public String parse(Chunk chunk) {
		return template.parse(chunk);
	}

}
