package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableRowTagTemplate;

public class TableRowTag implements Tag<TableRowChunk> {

	private TableRowTagTemplate template = new TableRowTagTemplate();
	
	@Override
	public String parse(TableRowChunk chunk) {
		return template.parse(chunk);
	}

}
