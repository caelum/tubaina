package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.parser.Tag;

public class QuestionTag implements Tag<QuestionChunk> {

	@Override
	public String parse(QuestionChunk chunk) {
		return "\\item{" + chunk.getContent() + "}";
	}

}
