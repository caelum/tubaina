package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class ItemChunk extends CompositeChunk<ItemChunk> {

	public ItemChunk(List<Chunk> body) {
		super(body);
	}

}
