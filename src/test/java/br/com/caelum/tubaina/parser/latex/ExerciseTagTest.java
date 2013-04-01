package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.ExerciseChunk;

public class ExerciseTagTest extends AbstractTagTest {

	@Test
	public void testExerciseTag(){
		ExerciseChunk chunk = new ExerciseChunk(text("texto do exercicio"));
		int lastExerciseCount = ExerciseChunk.getExerciseCount() - 1;
		String result = getContent(chunk);
		Assert.assertEquals("\\label{ex:" + lastExerciseCount + "}\n\\begin{enumerate}[1)]\ntexto do exercicio\n\\end{enumerate}", result);
	}

}
