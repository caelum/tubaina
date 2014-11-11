package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.parser.Tag;

public class QuestionTag implements Tag<QuestionChunk> {

	@Override
	public String parse(QuestionChunk chunk) {
		return "\n\n****PERGUNTA****\n\n" + chunk.getContent();
	}

}
