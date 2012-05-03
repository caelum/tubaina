package br.com.caelum.tubaina.parser.kindle;

import org.junit.Assert;
import org.junit.Test;


public class AnswerTagTest {
	@Test
	public void testAnswerTag(){
		AnswerTag tag = new AnswerTag();
		String result = tag.parse("texto da resposta", "0");
		Assert.assertEquals("<div class=\"answer\" " +
				"id=\"answer0\">texto da resposta</div><br/>", result);
	}
}


