package br.com.caelum.tubaina.parser.html;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.ExerciseTag;


public class ExerciseTagTest {
	
	@Test
	public void testExerciseTag(){
		ExerciseTag tag = new ExerciseTag();
		String result = tag.parse("texto do exercicio", null);
		Assert.assertEquals("<ol class=\"exercise\">texto do exercicio</ol>", result);
	}

}
