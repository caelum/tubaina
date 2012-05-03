package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ExerciseTagTemplate;

public class ExerciseTag implements Tag{
	private ExerciseTagTemplate template = new ExerciseTagTemplate();
	
	public String parse(String string, String options) {
		return template.parse(string, options);
	}

}
