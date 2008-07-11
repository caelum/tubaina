package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class TableColumnChunk implements CompositeChunk {

	private List<Chunk> cell;

	public TableColumnChunk(List<Chunk> cell) {
		this.cell = cell;
	}

	public String getContent(Parser p) {
		String content = "";
		for (Chunk c : cell) {
			content += c.getContent(p);
		}
		return p.parseColumn(content);
	}

}
