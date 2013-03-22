package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

public class CenteredParagraphTagTest {
	@Test
	public void testCenteredParagraphTag() {
		CenteredParagraphTag tag = new CenteredParagraphTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("\\begin{center}texto centralizado\\end{center}",
				result);
	}
}
