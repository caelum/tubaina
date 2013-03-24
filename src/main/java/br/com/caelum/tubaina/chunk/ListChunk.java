package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class ListChunk extends CompositeChunk<ListChunk> {

	private String type;

	public ListChunk(String type, List<Chunk> body) {
		super(body);
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
