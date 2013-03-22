package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ExerciseTagTemplate;

public class ExerciseTag implements Tag<ExerciseChunk> {
	private ExerciseTagTemplate template = new ExerciseTagTemplate();
	
	@Override
	public String parse(ExerciseChunk chunk) {
		return template.parse(chunk);
	}

}
