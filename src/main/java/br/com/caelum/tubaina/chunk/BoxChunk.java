package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class BoxChunk implements CompositeChunk {

	private final List<Chunk> body;

	private final String title;

	public BoxChunk(final String title, final List<Chunk> body) {
		this.title = title.trim();
		this.body = body;
	}

	public String getContent(final Parser p) {
		String content = "";
		for (Chunk c : body) {
			content += c.getContent(p);
		}
		return p.parseBox(content, title);
	}

}
