package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableRowTagTemplate;

public class TableRowTag implements Tag {

	private TableRowTagTemplate template = new TableRowTagTemplate();
	
	public String parse(Chunk chunk) {
		return template.parse(chunk);
	}

}
