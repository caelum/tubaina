package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableColumnTagTemplate implements Tag<TableColumnChunk> {

	@Override
	public String parse(TableColumnChunk chunk) {
		return "<td>" + chunk.getContent() + "</td>";
	}

}
