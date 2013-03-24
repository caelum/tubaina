package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.parser.Tag;

public class QuestionTagTemplate implements Tag<QuestionChunk> {

	@Override
	public String parse(QuestionChunk chunk) {
		return "<li class=\"question\">" + chunk.getContent() + "</li>";
	}

}
