package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class ExerciseTag implements Tag {

	public String parse(String string, String options) {
		return "<ol class=\"exercise\">" + string + "</ol>";
	}

}
