package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.CenteredParagraphTagTemplate;

public class CenteredParagraphTag implements Tag {
	private CenteredParagraphTagTemplate template = new  CenteredParagraphTagTemplate();
	
	public String parse(String string, String options) {
		return template.parse(string, options);
	}

}
