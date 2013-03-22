package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class QuestionChunk implements CompositeChunk {

	private List<Chunk> body;

	public QuestionChunk(List<Chunk> body) {
		this.body = body;
	}
	
	public String asString() {
		String content = "";
		for (Chunk c : body) {
			content += c.asString();
		}
		return p.parseQuestion(content);
	}

}
