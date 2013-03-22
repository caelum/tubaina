package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class NoteChunk implements CompositeChunk {

	private final List<Chunk> title;

	private final List<Chunk> body;

	public NoteChunk(List<Chunk> title, List<Chunk> body) {
		this.title = title;
		this.body = body;
	}

	public String asString() {
		String content =  "" + '\n';
		for (Chunk c : body) {
			content += c.asString();
		}
		String fullTitle = "";
		for (Chunk c : title) {
			fullTitle += c.asString();
		}
		return p.parseNote(content, fullTitle);
	}

}
