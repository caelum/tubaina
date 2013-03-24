package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableRowTagTemplate implements Tag<TableRowChunk> {

	@Override
	public String parse(TableRowChunk chunk) {
		return "<tr>" + chunk.getContent() + "</tr>";
	}

}
