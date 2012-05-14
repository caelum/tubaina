package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.resources.Resource;

public class TableReplacer extends AbstractReplacer {

	private List<Resource> resources;

	public TableReplacer(List<Resource> resources) {
		super("table");
		this.resources = resources;
	}
	
	@Override
	protected Chunk createChunk(String options, String content) {
		List<Chunk> rows = new ChunkSplitter(resources, ReplacerType.TABLE).splitChunks(content);
		return new TableChunk(options, rows);
	}
}
