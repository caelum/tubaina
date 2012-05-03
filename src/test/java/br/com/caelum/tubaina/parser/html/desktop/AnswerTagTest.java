package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.desktop.AnswerTag;


public class AnswerTagTest {
	@Test
	public void testAnswerTag(){
		AnswerTag tag = new AnswerTag();
		String result = tag.parse("texto da resposta", "0");
		Assert.assertEquals("<a class=\"answer\" onclick=\"toogleAnswer('answer0');\">" +
				"Click here for the answer</a><br /><div class=\"answer\" " +
				"id=\"answer0\">texto da resposta</div><br/>", result);
	}
}


