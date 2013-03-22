package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class ItemChunk implements Chunk {

	private List<Chunk> body;

	public ItemChunk(List<Chunk> body) {
		this.body = body;
	}

	public String asString(Parser p) {
		String content = "";
		for (Chunk c : body) {
			content += c.asString();
		}
		return p.parseItem(content);
	}

}
