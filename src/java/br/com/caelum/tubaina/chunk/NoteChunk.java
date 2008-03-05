package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class NoteChunk implements CompositeChunk {

	private final List<Chunk> title;

	private final List<Chunk> body;

	public NoteChunk(List<Chunk> title, List<Chunk> body) {
		this.title = title;
		this.body = body;
	}

	public String getContent(Parser p) {
		String content =  "";
		for (Chunk c : title) {
			content += c.getContent(p);
		}
		content += '\n';
		for (Chunk c : body) {
			content += c.getContent(p);
		}		
		return p.parseNote(content);
	}

}
