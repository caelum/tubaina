package br.com.caelum.tubaina.parser.html.desktop;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.CodeHighlightTag;


public class CodeHighlightTagTest {

	@Test
	public void testHighlight() throws Exception {
		String string = "Primeira linha\n" +
			"Segunda linha\n" +
			"Terceira linha\n" +
			"Quarta linha";
		String output = new CodeHighlightTag().parseHtml(string, Arrays.asList(1, 3));
		Assert.assertEquals("<strong>Primeira linha</strong>\n" +
				"Segunda linha\n" +
				"<strong>Terceira linha</strong>\n" +
				"Quarta linha", output);
	}
	
	@Test
	public void testHighlightWithNullParams() throws Exception {
		String output = new CodeHighlightTag().parseHtml("", null);
		Assert.assertEquals("", output);
	}
	
	@Test
	public void testInvalidLineNumbers() throws Exception {
		String input = "Só uma linha";
		String output = new CodeHighlightTag().parseHtml(input, Arrays.asList(23));
		Assert.assertEquals(input, output);
	}
}
