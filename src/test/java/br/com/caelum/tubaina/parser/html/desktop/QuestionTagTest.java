package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.QuestionChunk;


public class QuestionTagTest extends AbstractTagTest {

	@Test
	public void testQuestionTag() {
		QuestionChunk chunk = new QuestionChunk(text("texto da questao"));
		String result = getContent(chunk);
		Assert.assertEquals("<li class=\"question\">texto da questao</li>", result);
	}

	
}
