package br.com.caelum.tubaina.parser.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class AnswerTag implements Tag {
	private final String answerText = "Click here for the answer";
	
	public String parse(String string, String id) {
		return "<div class=\"answer\" id=\"answer" + id
		+ "\">" + string + "</div><br/>";
	}

}
