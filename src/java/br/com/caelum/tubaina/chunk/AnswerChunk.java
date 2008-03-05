package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class AnswerChunk implements CompositeChunk {

	private static int ANSWER_ID = 0;

	private List<Chunk> body;

	private int id;

	public AnswerChunk(List<Chunk> body) {
		this.body = body;
		this.id = ANSWER_ID++;
	}

	public String getContent(Parser p) {
		String content = "";
		for (Chunk c : body) {
			content += c.getContent(p);
		}
		
		return p.parseAnswer(content, id);
	}

	public String getRealContent(Parser p) {
		String content = "";
		for (Chunk c : body) {
			content += c.getContent(p);
		}
		return content;
	}
	
	public int getId() {
		return id;
	}

}
