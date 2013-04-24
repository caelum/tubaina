package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.parser.Tag;

public class AnswerTag implements Tag<AnswerChunk> {
 
	@Override
	public String parse(AnswerChunk chunk) {
		return "<div class=\"answer\" id=\"answer" + chunk.getId()
		+ "\">" + chunk.getContent() + "</div><br/>";
	}

}
