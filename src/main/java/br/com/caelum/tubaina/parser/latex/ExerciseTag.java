package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ExerciseTag implements Tag<ExerciseChunk> {

	@Override
	public String parse(ExerciseChunk chunk) {

		return "\\label{ex:" + chunk.getId() + "}\n\\begin{enumerate}[1)]\n" 
				+ chunk.getContent() + "\n\\end{enumerate}";
	}

}
