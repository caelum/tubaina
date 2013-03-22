package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class QuestionTagTemplate implements Tag {

	public String parse(Chunk chunk) {
		return "<li class=\"question\">" + string + "</li>";
	}

}
