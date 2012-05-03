package br.com.caelum.tubaina.parser.kindle;

import org.junit.Assert;
import org.junit.Test;

public class CenteredParagraphTagTest {
	@Test
	public void testCenteredParagraphTest() {
		CenteredParagraphTag tag = new CenteredParagraphTag();
		String result = tag.parse("texto centralizado", null);
		Assert.assertEquals("<p class=\"center\">texto centralizado</p>", result);
	}
}
