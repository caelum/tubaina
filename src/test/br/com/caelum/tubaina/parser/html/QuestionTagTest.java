package br.com.caelum.tubaina.parser.html;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.QuestionTag;


public class QuestionTagTest {

	@Test
	public void testQuestionTag() {
		QuestionTag tag = new QuestionTag();
		Assert.assertEquals("<li>texto da questao</li>", tag.parse("texto da questao", null));
	}

	
}
