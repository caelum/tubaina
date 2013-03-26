package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;

public class CenteredParagraphTagTest extends AbstractTagTest {
	@Test
	public void testCenteredParagraphTag() {
		CenteredParagraphChunk chunk = new CenteredParagraphChunk("texto centralizado");
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{center}texto centralizado\\end{center}", result);
	}
}
