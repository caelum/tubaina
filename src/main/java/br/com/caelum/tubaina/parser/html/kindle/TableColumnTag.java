package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableColumnTagTemplate;

public class TableColumnTag implements Tag {

	private TableColumnTagTemplate template = new TableColumnTagTemplate();
	
	public String parse(Chunk chunk) {
		return template.parse(chunk);
	}

}
