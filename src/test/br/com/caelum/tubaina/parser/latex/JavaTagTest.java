package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;


public class JavaTagTest {

	@Test
	public void testGenerateLatexFromJavaCode() {
		String content = (new JavaTag(new SimpleIndentator()).parse("\nclass X {}", ""));
		Assert.assertTrue(content.contains("jttstylee"));
		Assert.assertTrue(content.contains("class"));
		Assert.assertTrue(content.contains("X"));
	}
}
