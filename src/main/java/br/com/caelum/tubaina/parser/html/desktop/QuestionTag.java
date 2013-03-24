package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.QuestionTagTemplate;

public class QuestionTag implements Tag<QuestionChunk> {

	private QuestionTagTemplate template = new QuestionTagTemplate();
	
	@Override
	public String parse(QuestionChunk chunk) {
		return template.parse(chunk);
	}

}
