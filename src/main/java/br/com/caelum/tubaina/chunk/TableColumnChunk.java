package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class TableColumnChunk extends CompositeChunk<TableColumnChunk> {

	public TableColumnChunk(List<Chunk> cell) {
		super(cell);
	}
}
