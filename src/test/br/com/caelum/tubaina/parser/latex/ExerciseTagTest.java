package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

public class ExerciseTagTest {
	@Test
	public void testExerciseTag(){
		ExerciseTag tag = new ExerciseTag();
		String result = tag.parse("texto do exercicio", "1");
		Assert.assertEquals("\\label{ex:1}\n\\begin{enumerate}[1)]\ntexto do exercicio\n\\end{enumerate}", result);
	}

}
