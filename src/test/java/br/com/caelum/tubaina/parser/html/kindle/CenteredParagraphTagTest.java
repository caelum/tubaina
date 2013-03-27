package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;

public class CenteredParagraphTagTest extends AbstractTagTest {
	@Test
	public void testCenteredParagraphTest() {
		CenteredParagraphChunk chunk = new CenteredParagraphChunk("texto centralizado");
		String result = getContent(chunk);
		Assert.assertEquals("<p class=\"center\">texto centralizado</p>", result);
	}
}
