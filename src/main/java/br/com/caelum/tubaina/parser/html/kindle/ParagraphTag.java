package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ParagraphTagTemplate;

public class ParagraphTag implements Tag {

	private ParagraphTagTemplate template = new ParagraphTagTemplate();
	
	public String parse(String string, String options) {
		return template.parse(string, options);
	}

}
