package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class BoxChunk extends CompositeChunk<BoxChunk> {

	private final String title;

	public BoxChunk(final String title, final List<Chunk> body) {
		super(body);
		this.title = title.trim();
	}

	public String getTitle() {
		return title;
	}
}
