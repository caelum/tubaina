package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.kindle.ExerciseTag;


public class ExerciseTagTest {
	
	@Test
	public void testExerciseTag(){
		String result = new ExerciseTag().parse(chunk);
		Assert.assertEquals("<ol class=\"exercise\">texto do exercicio</ol>", result);
	}

}
