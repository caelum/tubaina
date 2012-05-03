package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.QuestionTagTemplate;

public class QuestionTag implements Tag {

	private QuestionTagTemplate template = new QuestionTagTemplate();
	
	public String parse(String string, String options) {
		return template.parse(string, options);
	}

}
