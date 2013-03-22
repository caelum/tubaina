package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.QuestionTagTemplate;

public class QuestionTag implements Tag {

	private QuestionTagTemplate template = new QuestionTagTemplate();
	
	public String parse(Chunk chunk) {
		return template.parse(chunk);
	}

}
