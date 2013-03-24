package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class TableRowChunk extends CompositeChunk<TableRowChunk> {

	public TableRowChunk(List<Chunk> cols) {
		super(cols);
	}

	public int getNumberOfColumns() {
		int columns = 0;
		for (Chunk c : body) {
			if (c.getClass().equals(TableColumnChunk.class))
				columns++;
		}
		return columns;
	}

}
