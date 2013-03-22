package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ExerciseTagTemplate implements Tag<ExerciseChunk> {

	@Override
	public String parse(ExerciseChunk chunk) {
		return "<ol class=\"exercise\">" + chunk.getContent() + "</ol>";
	}

}
