package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ItemTagTemplate;

public class ItemTag implements Tag {
	private ItemTagTemplate template = new ItemTagTemplate();
	
	public String parse(String string, String options) {
		return template.parse(string, options);
	}
}
