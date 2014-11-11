package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableRowTag implements Tag<TableRowChunk> {

	@Override
	public String parse(TableRowChunk chunk) {
		throw new TubainaException("tables are not yet supported");
	}

}
