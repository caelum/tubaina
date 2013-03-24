package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class NoteChunk extends CompositeChunk<NoteChunk> {

	private final List<Chunk> title;

	public NoteChunk(List<Chunk> title, List<Chunk> body) {
		super(body);
		this.title = title;
	}

}
