package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class BoxChunk implements CompositeChunk {


	private final List<Chunk> body;
	private String title;
	
	public BoxChunk(String title, List<Chunk> body) {
		this.title = title;
		this.body = body;
	}

	public String getContent(Parser p) {
		String title =  this.title.trim();
		String content = "";
		for (Chunk c : body) {
			content += c.getContent(p);
		}		
		return p.parseBox(content, title);
	}

}
