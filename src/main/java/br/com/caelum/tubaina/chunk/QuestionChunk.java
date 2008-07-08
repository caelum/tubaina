package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class QuestionChunk implements CompositeChunk {

	private List<Chunk> body;

	public QuestionChunk(List<Chunk> body) {
		this.body = body;
	}
	
	public String getContent(Parser p) {
		String content = "";
		for (Chunk c : body) {
			content += c.getContent(p);
		}
		return p.parseQuestion(content);
	}

}
