package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class QuestionTagTemplate implements Tag {

	public String parse(String string, String options) {
		return "<li class=\"question\">" + string + "</li>";
	}

}
