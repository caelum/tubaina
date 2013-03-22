package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class TableColumnChunk implements CompositeChunk {

	private List<Chunk> cell;

	public TableColumnChunk(List<Chunk> cell) {
		this.cell = cell;
	}

	public String asString() {
		String content = "";
		for (Chunk c : cell) {
			content += c.asString();
		}
		return p.parseColumn(content);
	}

}
