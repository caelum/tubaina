package br.com.caelum.tubaina.parser.html;

import org.junit.Assert;
import org.junit.Test;

public class CenteredParagraphTagTest {
	@Test
	public void testCenteredParagraphTest() {
		CenteredParagraphTag tag = new CenteredParagraphTag();
		String result = tag.parse("texto centralizado", null);
		Assert.assertEquals("<div style=\"text-align:center\">texto centralizado</div>", result);
	}
}
