package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.resources.Resource;

public class TableRowReplacer extends AbstractReplacer {

	private List<Resource> resources;

	public TableRowReplacer(List<Resource> resources) {
		super("row");
		this.resources = resources;
	}

	@Override
	protected Chunk createChunk(String options, String content) {
		List<Chunk> cols = new ChunkSplitter(resources, ReplacerType.ROW).splitChunks(content);
		if (cols.size() == 0)
			throw new TubainaException("Row without columns inside");
		return new TableRowChunk(cols);
	}

}
