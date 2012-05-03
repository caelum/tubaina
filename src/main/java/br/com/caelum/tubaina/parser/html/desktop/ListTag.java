package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ListTagTemplate;

public class ListTag implements Tag {

	private ListTagTemplate template = new ListTagTemplate();
	
	public String parse(String content, String options) {
		return template.parse(content, options);
	}

}
