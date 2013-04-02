package br.com.caelum.tubaina.parser;

import br.com.caelum.tubaina.chunk.AnswerChunk;

//TODO: unify with NullNoteTag
public class NullAnswerTag implements Tag<AnswerChunk>{

	@Override
	public String parse(AnswerChunk chunk) {
		return "";
	}

}
