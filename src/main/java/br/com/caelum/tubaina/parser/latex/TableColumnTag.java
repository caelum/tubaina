package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableColumnTag implements Tag<TableColumnChunk> {

	@Override
	public String parse(TableColumnChunk chunk) {
		return chunk.getContent() + "& ";
	}

}
