package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.desktop.CenteredParagraphTag;

public class CenteredParagraphTagTest {
	@Test
	public void testCenteredParagraphTest() {
		CenteredParagraphTag tag = new CenteredParagraphTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("<p class=\"center\">texto centralizado</p>", result);
	}
}
