package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;


public class CodeTagTest {

	@Test
	public void testPropertiesCodeTag() throws Exception {
		String options = "properties";
		String string = "blablah blah\n" +
				"#algum comentario\n" +
				"texto=valor\n" +
				"texto:valor\n" +
				"texto valor";
		String output = new CodeTag(new SimpleIndentator()).parse(string, options);
		Assert.assertEquals(CodeTag.BEGIN + 
				"\\black blablah~\\texvalue blah\\\\\n" +
				"\\black \\texcomment \\#algum~comentario\\\\\n" +
				"\\black texto\\verb#=#\\texvalue valor\\\\\n" +
				"\\black texto:\\texvalue valor\\\\\n" +
				"\\black texto~\\texvalue valor" + CodeTag.END, output);
		
	}
	@Test
	public void testPropertiesCodeTagWithEscapes() throws Exception {
		String options = "properties abc";
		String string = "blablah blah\n" +
				"#algum comentario\n" +
				"texto\\=valor=valor\n" +
				"texto\\:valor:valor\n" +
				"texto\\ valor valor\n" +
				"a b\\#fake comentario";
		String output = new CodeTag(new SimpleIndentator()).parse(string, options);
		Assert.assertEquals(CodeTag.BEGIN + 
				"\\black blablah~\\texvalue blah\\\\\n" +
				"\\black \\texcomment \\#algum~comentario\\\\\n" +
				"\\black texto\\char92\\verb#=#valor\\verb#=#\\texvalue valor\\\\\n" +
				"\\black texto\\char92:valor:\\texvalue valor\\\\\n" +
				"\\black texto\\char92~valor~\\texvalue valor\\\\\n" +
				"\\black a~\\texvalue b\\char92\\#fake~comentario" + CodeTag.END, output);
		
	}
}
