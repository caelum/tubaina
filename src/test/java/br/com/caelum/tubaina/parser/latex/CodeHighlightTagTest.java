package br.com.caelum.tubaina.parser.latex;

import java.util.Arrays;
import java.util.Collections;

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
		String output = new CodeHighlightTag().parseLatex(string, Arrays.asList(1, 3));
		Assert.assertEquals("\\colorbox{yellow}{Primeira linha}\\\n" +
				"Segunda linha\n" +
				"\\colorbox{yellow}{Terceira linha}\\\n" +
				"Quarta linha", output);
	}
	
	@Test
	public void testHighlightWithNullParams() throws Exception {
		String output = new CodeHighlightTag().parseLatex("", null);
		Assert.assertEquals("", output);
	}
	
	@Test
	public void testInvalidLineNumbers() throws Exception {
		String input = "Só uma linha";
		String output = new CodeHighlightTag().parseLatex(input, Collections.singletonList(23));
		Assert.assertEquals(input, output);
	}
}
