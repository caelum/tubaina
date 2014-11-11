package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableColumnTag implements Tag<TableColumnChunk> {

	@Override
	public String parse(TableColumnChunk chunk) {
		throw new TubainaException("tables are not yet supported");
	}

}
