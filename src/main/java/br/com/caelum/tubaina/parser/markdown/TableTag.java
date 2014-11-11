package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableTag implements Tag<TableChunk> {

	@Override
	public String parse(TableChunk chunk) {
		throw new TubainaException("tables are not yet supported");
	}

}
