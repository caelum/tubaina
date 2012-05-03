package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.BoxTagTemplate;

public class BoxTag implements Tag {

	private BoxTagTemplate template = new BoxTagTemplate();
	
	public String parse(String content, String title) {
		return template.parse(content, title);
	}

}
