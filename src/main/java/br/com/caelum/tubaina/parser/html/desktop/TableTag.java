package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableTagTemplate;

public class TableTag implements Tag {

	private TableTagTemplate template;
	
	public TableTag(boolean noborder) {
		 template = new TableTagTemplate(noborder);
	}

	public String parse(String string, String options) {
		return template.parse(string, options);
	}

}
