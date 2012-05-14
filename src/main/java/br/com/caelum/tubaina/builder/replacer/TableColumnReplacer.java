package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.resources.Resource;

public class TableColumnReplacer extends AbstractReplacer {
	
	private List<Resource> resources;

	public TableColumnReplacer(List<Resource> resources) {
		super("col");
		this.resources = resources;
	}

	@Override
	protected Chunk createChunk(String options, String content) {
		List<Chunk> cell = new ChunkSplitter(resources, ReplacerType.COLUMN).splitChunks(content);
		return new TableColumnChunk(cell);
	}

}
