package br.com.caelum.tubaina.util;

import junit.framework.Assert;

import org.junit.Test;


public class HtmlSanitizerTest {

	@Test
	public void testEscapes() throws Exception {
		String input = "&&&&";
		String output = new HtmlSanitizer().sanitize(input);
		Assert.assertEquals("&amp;&amp;&amp;&amp;", output);
	}

	@Test
	public void sanitizingNullStringShouldReturnEmptyText() throws Exception {
		String input = null;
		String output = new HtmlSanitizer().sanitize(input);
		Assert.assertEquals("", output);
	}
}
