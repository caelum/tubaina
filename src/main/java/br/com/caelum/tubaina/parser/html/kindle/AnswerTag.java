package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class AnswerTag implements Tag {
 
	public String parse(String string, String id) {
		return "<div class=\"answer\" id=\"answer" + id
		+ "\">" + string + "</div><br/>";
	}

}
