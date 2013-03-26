package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.ExerciseChunk;


public class ExerciseTagTest extends AbstractTagTest {
	
	@Test
	public void testExerciseTag(){
		ExerciseChunk chunk = new ExerciseChunk(text("texto do exercicio"));
		String result = getContent(chunk);
		Assert.assertEquals("<ol class=\"exercise\">texto do exercicio</ol>", result);
	}

}
