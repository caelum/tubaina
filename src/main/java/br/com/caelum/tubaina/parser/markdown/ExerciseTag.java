package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ExerciseTag implements Tag<ExerciseChunk> {

	@Override
	public String parse(ExerciseChunk chunk) {

		return  "\n\n### Exercício\n\n" + 
				chunk.getContent() + 
				"\n\n";
	}

}
