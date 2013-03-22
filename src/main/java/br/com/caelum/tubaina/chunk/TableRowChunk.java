package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class TableRowChunk implements CompositeChunk {

	private List<Chunk> cols;

	public TableRowChunk(List<Chunk> cols) {
		this.cols = cols;
	}

	public String asString() {
		String content = "";
		for (Chunk c : cols) {
			content += c.asString();
		}
		return p.parseRow(content);
	}

	public int getNumberOfColumns() {
		int columns = 0;
		for (Chunk c : cols) {
			if (c.getClass().equals(TableColumnChunk.class))
				columns++;
		}
		return columns;
	}

}
