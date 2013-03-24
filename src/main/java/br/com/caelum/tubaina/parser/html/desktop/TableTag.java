package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableTagTemplate;

public class TableTag implements Tag<TableChunk> {

	private TableTagTemplate template;
	
	public TableTag(boolean noborder) {
		 template = new TableTagTemplate(noborder);
	}

	@Override
	public String parse(TableChunk chunk) {
		return template.parse(chunk);
	}

}
