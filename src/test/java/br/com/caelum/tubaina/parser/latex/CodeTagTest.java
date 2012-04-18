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
	public void languageCodeTagShouldUnderstandLineNumbersEvenWhenNoLanguageIsSelected(){
		String string = "def some: \"bizarre code\" in: unknownLanguage";
		String options = "#";
		String output = new CodeTag(new SimpleIndentator()).parse(string , options );
		Assert.assertEquals(CodeTag.BEGIN + "[linenos, numbersep=5pt]{text}\n" +
				string + 
				CodeTag.END, output);
	}

	@Test
	public void languageCodeTagShouldUnderstandLineNumbersAndHighlightsWhenNoLanguageIsSelected(){
		String string = "def some: \"bizarre code\" \nin: unknownLanguage";
		String options = "# h=1,2";
		String output = new CodeTag(new SimpleIndentator()).parse(string , options );
		Assert.assertEquals(CodeTag.BEGIN + "[linenos, numbersep=5pt, h=1,2]{text}\n" +
				string + 
				CodeTag.END, output);
	}
	
	@Test
	public void languageCodeTagShouldUnderstandLineNumbersAndCSharpLanguage(){
		String string = "public class SomeClass {";
		String options = "C# #";
		String output = new CodeTag(new SimpleIndentator()).parse(string , options );
		Assert.assertEquals(CodeTag.BEGIN + "[linenos, numbersep=5pt]{C#}\n" +
				string + 
				CodeTag.END, output);
	}
	
    @Test
    public void codeTagWithReferenceWithoutLanguage() throws Exception {
        String options = "label=javacode1";
        String code = "class Main {\n" + "public static void main(String[] args) {\n"
                + "System.out.println(\"Hello world\");\n" + "}\n}";
        String output = new CodeTag(new SimpleIndentator()).parse(code, options);
        Assert.assertEquals(CodeTag.CODEREFERENCE + "\\label{javacode1}\n" + CodeTag.BEGIN
                + "{text}\n" + code + CodeTag.END,
                output);

    }
	
    @Test
    public void codeTagWithReferenceWithLanguage() throws Exception {
        String options = "java label=javacode1";
        String code = "class Main {\n" + "public static void main(String[] args) {\n"
                + "System.out.println(\"Hello world\");\n" + "}\n}";
        String output = new CodeTag(new SimpleIndentator()).parse(code, options);
        Assert.assertEquals(CodeTag.CODEREFERENCE + "\\label{javacode1}\n" + CodeTag.BEGIN
                + "{java}\n" + code + CodeTag.END, output);

    }

	//TODO: file name as an option to code
}
