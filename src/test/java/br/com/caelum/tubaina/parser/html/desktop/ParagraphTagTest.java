package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.ParagraphChunk;

public class ParagraphTagTest extends AbstractTagTest {

	@Test
	public void testParagraphTag(){
		ParagraphChunk chunk = new ParagraphChunk("qualquer texto");
		String result = getContent(chunk);
		Assert.assertEquals(result, "<p>qualquer texto</p>");
	}
	
}
