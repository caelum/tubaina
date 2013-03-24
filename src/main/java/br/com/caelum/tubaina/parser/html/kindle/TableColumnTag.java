package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableColumnTagTemplate;

public class TableColumnTag implements Tag<TableColumnChunk> {

	private TableColumnTagTemplate template = new TableColumnTagTemplate();
	
	@Override
	public String parse(TableColumnChunk chunk) {
		return template.parse(chunk);
	}

}
