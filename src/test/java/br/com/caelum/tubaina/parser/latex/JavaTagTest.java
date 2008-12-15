package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;


public class JavaTagTest {

	@Test
	public void testGenerateLatexFromJavaCode() {
		String content = (new JavaTag(new SimpleIndentator()).parse("\nclass X {}", ""));
		Assert.assertTrue(content.contains("\\jttstylee"));
		Assert.assertTrue(content.contains("class"));
		Assert.assertTrue(content.contains("X"));
	}
	
	@Test
	public void testDecrementOperatorIsCorrectlyEscaped() {
		String content = new JavaTag(new SimpleIndentator()).parse("i--;\n--i;", "");
		Assert.assertTrue("Should be escaped", content.contains("i{\\verb#-#}{\\verb#-#};"));
		Assert.assertTrue("Should be escaped", content.contains("{\\verb#-#}{\\verb#-#}i;"));
	}
}
