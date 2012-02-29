package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
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
		Assert.assertEquals(CodeTag.BEGIN + "{properties}\n" +
				"blablah blah\n" +
				"#algum comentario\n" +
				"texto=valor\n" +
				"texto:valor\n" +
				"texto valor" + CodeTag.END, output);
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
		Assert.assertEquals(CodeTag.BEGIN + "{properties}\n" +
				"blablah blah\n" +
				"#algum comentario\n" +
				"texto\\=valor=valor\n" +
				"texto\\:valor:valor\n" +
				"texto\\ valor valor\n" +
				"a b\\#fake comentario" + CodeTag.END, output);
		
	}
	
	@Test
	public void languageCodeTagIsReturnedInsideMintedEnvironment() throws Exception {
		String string = "public static void main(String[] args) {";
		String options = "java";
		String output = new CodeTag(new SimpleIndentator()).parse(string , options );
		Assert.assertEquals(CodeTag.BEGIN + "{java}\n" +
							string + 
							CodeTag.END, output);
	}
	
	@Test
	public void languageCodeTagShouldInsertLineNumbersWhenOptionContainsSharp(){
		String string = "public static void main(String[] args) {";
		String options = "java #";
		String output = new CodeTag(new SimpleIndentator()).parse(string , options );
		Assert.assertEquals(CodeTag.BEGIN + "[linenos, numbersep=5pt]{java}\n" +
							string + 
							CodeTag.END, output);
	}
	
	@Test
	public void codeWithoutDefinedLanguageWillBeTextInsideMintedEnvironment() throws Exception {
		String string = "public static void main(String[] args) {";
		String options = "";
		String output = new CodeTag(new SimpleIndentator()).parse(string , options );
		Assert.assertEquals(CodeTag.BEGIN + "{text}\n" +
							string + 
							CodeTag.END, output);
	}
	
}
