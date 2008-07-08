package br.com.caelum.tubaina.parser.html;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;


public class CodeTagTest {

	@Test
	public void testCodeWithHighlights() throws Exception {
		String options = "h=1,3,5\n";
		String code = "Algum texto\n" +
				"Em várias linhas\n" +
				"Mas não necessariamente 5";
		String output = new CodeTag(new SimpleIndentator()).parse(code, options);
		Assert.assertEquals(CodeTag.BEGIN + "<strong>Algum&nbsp;texto<br /></strong>\n" +
				"Em&nbsp;várias&nbsp;linhas<br />\n" +
				"<strong>Mas&nbsp;não&nbsp;necessariamente&nbsp;5</strong>" + CodeTag.END, output);
	}
}
