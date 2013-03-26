package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.CodeChunk;

public class CodeTagTest extends AbstractTagTest {

	@Test
	public void testPropertiesCodeTag() throws Exception {
		String options = "properties";
		String content = "blablah blah\n" + "#algum comentario\n" + "texto=valor\n" + "texto:valor\n" + "texto valor";
		CodeChunk chunk = new CodeChunk(content, options);
		String output = getContent(chunk);

		assertPygmentsRan(output);
	}

	@Test
	public void testPropertiesCodeTagWithEscapes() throws Exception {
		String options = "properties abc";
		String string = "blablah blah\n" + "#algum comentario\n" + "texto\\=valor=valor\n" + "texto\\:valor:valor\n"
				+ "texto\\ valor valor\n" + "a b\\#fake comentario";
		CodeChunk chunk = new CodeChunk(string, options);
		String output = getContent(chunk);

		assertPygmentsRan(output);
	}

	@Test
	public void languageCodeTagShouldInsertLineNumbersWhenOptionContainsSharp() {
		String string = "public static void main(String[] args) {";
		String options = "java #";
		CodeChunk chunk = new CodeChunk(string, options);
		String output = getContent(chunk);

		assertPygmentsRan(output);
	}

	@Test
	public void languageCodeTagShouldUnderstandLineNumbersEvenWhenNoLanguageIsSelected() {
		String string = "def some: \"bizarre code\" in: unknownLanguage";
		String options = "#";
		CodeChunk chunk = new CodeChunk(string, options);
		String output = getContent(chunk);

		assertPygmentsRan(output);
	}

	@Test
	public void languageCodeTagShouldUnderstandLineNumbersAndHighlightsWhenNoLanguageIsSelected() {
		String string = "def some: \"bizarre code\" \nin: unknownLanguage";
		String options = "# h=1,2";
		CodeChunk chunk = new CodeChunk(string, options);
		String output = getContent(chunk);

		assertPygmentsRan(output);
	}

	@Test
	public void languageCodeTagShouldUnderstandLineNumbersAndCSharpLanguage() {
		String string = "public class SomeClass {";
		String options = "c# #";
		CodeChunk chunk = new CodeChunk(string, options);
		String output = getContent(chunk);

		assertPygmentsRan(output);
	}

	@Test
	public void codeTagWithReferenceWithoutLanguage() throws Exception {
		String options = "label=javacode1";
		String code = "class Main {\n" + "public static void main(String[] args) {\n"
				+ "System.out.println(\"Hello world\");\n" + "}\n}";
		CodeChunk chunk = new CodeChunk(code, options);
		String output = getContent(chunk);

		assertTrue(output.startsWith("\\tubainaCodeLabel{javacode1}"));
		assertPygmentsRan(output);
	}

	@Test
	public void codeTagWithReferenceWithLanguage() throws Exception {
		String options = "java label=javacode1";
		String code = "class Main {\n" + "public static void main(String[] args) {\n"
				+ "System.out.println(\"Hello world\");\n" + "}\n}";
		CodeChunk chunk = new CodeChunk(code, options);
		String output = getContent(chunk);


		assertTrue(output.startsWith("\\tubainaCodeLabel{javacode1}"));
		assertPygmentsRan(output);
	}

	@Test
	public void codeTagWithFileNameWithoutLanguage() throws Exception {
		String options = "filename=src/Main.java";
		String code = "class Main {\n" + "public static void main(String[] args) {\n"
				+ "System.out.println(\"Hello world\");\n" + "}\n}";
		CodeChunk chunk = new CodeChunk(code, options);
		String output = getContent(chunk);

		assertTrue(output.startsWith("\\tubainaCodeFileName{src/Main.java}\n"));
		assertPygmentsRan(output);
	}

	@Test
	public void codeTagWithFileNameWithLanguage() throws Exception {
		String options = "java filename=src/Main.java";
		String code = "class Main {\n" + "public static void main(String[] args) {\n"
				+ "System.out.println(\"Hello world\");\n" + "}\n}";
		CodeChunk chunk = new CodeChunk(code, options);
		String output = getContent(chunk);

		assertTrue(output.startsWith("\\tubainaCodeFileName{src/Main.java}\n"));
		assertPygmentsRan(output);
	}

	@Test
	public void codeTagWithFileNameWithLanguageAndLabel() throws Exception {
		String options = "java filename=src/Main2.java label=javacode1";
		String code = "class Main {\n" + "public static void main(String[] args) {\n"
				+ "System.out.println(\"Hello world\");\n" + "}\n}";
		CodeChunk chunk = new CodeChunk(code, options);
		String output = getContent(chunk);

		assertTrue(output.startsWith("\\tubainaCodeLabel{javacode1}\n"));
		assertPygmentsRan(output);
	}

	@Test
	public void javascriptLangBug() throws Exception {
		String options = "javascript";
		String code = "writeTotal(3.14159);";
		CodeChunk chunk = new CodeChunk(code, options);
		String output = getContent(chunk);

		assertFalse(output.contains("javascript"));
		assertPygmentsRan(output);
	}

	private void assertPygmentsRan(String output) {
		assertTrue(output.contains("\\begin{Verbatim}[commandchars="));
		assertTrue(output.contains("\\end{Verbatim}"));
	}
	// TODO: file name as an option to code
}
