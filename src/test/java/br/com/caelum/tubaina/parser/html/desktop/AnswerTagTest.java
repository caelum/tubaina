package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.AnswerChunk;


public class AnswerTagTest extends AbstractTagTest {
	@Test
	public void testAnswerTag(){
		AnswerChunk chunk = new AnswerChunk(text("texto da resposta"));
		String result = getContent(chunk);
		Assert.assertEquals("<a class=\"answer\" onclick=\"toogleAnswer('answer0');\">" +
				"Click here for the answer</a><br /><div class=\"answer\" " +
				"id=\"answer0\">texto da resposta</div><br/>", result);
	}
}


