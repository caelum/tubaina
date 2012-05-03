package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableColumnTagTemplate;

public class TableColumnTag implements Tag {

	private TableColumnTagTemplate template = new TableColumnTagTemplate();
	
	public String parse(String text, String options) {
		return template.parse(text, options);
	}

}
