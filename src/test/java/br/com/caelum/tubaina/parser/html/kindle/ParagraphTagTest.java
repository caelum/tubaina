package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

public class ParagraphTagTest {

	@Test
	public void testParagraphTag(){
		String result = new ParagraphTag().parse(chunk);
		Assert.assertEquals(result, "<p>qualquer texto</p>");
	}
	
}
