package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class ListChunk implements CompositeChunk {

	
	private List<Chunk> body;
	private String type;

	public ListChunk(String type, List<Chunk> body) {
		this.body = body;
		this.type = type;
	}

	public String asString() {
		String content = "";
		for (Chunk c : body) {
			content += c.asString();
		}
		return p.parseList(content, type);
	}

}
