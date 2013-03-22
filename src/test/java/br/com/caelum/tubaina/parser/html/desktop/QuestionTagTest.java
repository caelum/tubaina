package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.desktop.QuestionTag;


public class QuestionTagTest {

	@Test
	public void testQuestionTag() {
		String result = new QuestionTag().parse(chunk);
		Assert.assertEquals("<li class=\"question\">texto da questao</li>", result);
	}

	
}
