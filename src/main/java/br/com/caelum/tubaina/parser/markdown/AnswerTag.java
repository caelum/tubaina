package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.parser.Tag;

public class AnswerTag implements Tag<AnswerChunk> {

	@Override
	public String parse(AnswerChunk chunk) {
		return "\n\n:: Resposta: :: " + chunk.getContent() + "\n\n";
	}

}
