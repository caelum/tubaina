package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.parser.Tag;

//TODO: unify with NullNoteTag
public class NullAnswerTag implements Tag<AnswerChunk>{

	@Override
	public String parse(AnswerChunk chunk) {
		return "";
	}

}
