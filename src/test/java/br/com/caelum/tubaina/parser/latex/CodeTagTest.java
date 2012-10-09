package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.html.desktop.CodeCache;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;
import br.com.caelum.tubaina.util.CommandExecutor;

public class CodeTagTest {

    private CodeTag codeTag;

    @Before
    public void setUp() {
        this.codeTag = new CodeTag(new SimpleIndentator(4), new SyntaxHighlighter(
                new CommandExecutor(), SyntaxHighlighter.LATEX_OUTPUT, false, new CodeCache(SyntaxHighlighter.LATEX_OUTPUT)));
    }

    @Test
    public void testPropertiesCodeTag() throws Exception {
        String options = "properties";
        String string = "blablah blah\n" + "#algum comentario\n" + "texto=valor\n"
                + "texto:valor\n" + "texto valor";
        String output = codeTag.parse(string, options);

        assertPygmentsRan(output);
    }

    @Test
    public void testPropertiesCodeTagWithEscapes() throws Exception {
        String options = "properties abc";
        String string = "blablah blah\n" + "#algum comentario\n" + "texto\\=valor=valor\n"
                + "texto\\:valor:valor\n" + "texto\\ valor valor\n" + "a b\\#fake comentario";
        String output = codeTag.parse(string, options);

        assertPygmentsRan(output);
    }

    @Test
    public void languageCodeTagShouldInsertLineNumbersWhenOptionContainsSharp() {
        String string = "public static void main(String[] args) {";
        String options = "java #";
        String output = codeTag.parse(string, options);

        assertPygmentsRan(output);
    }

    @Test
    public void languageCodeTagShouldUnderstandLineNumbersEvenWhenNoLanguageIsSelected() {
        String string = "def some: \"bizarre code\" in: unknownLanguage";
        String options = "#";
        String output = codeTag.parse(string, options);

        assertPygmentsRan(output);
    }

    @Test
    public void languageCodeTagShouldUnderstandLineNumbersAndHighlightsWhenNoLanguageIsSelected() {
        String string = "def some: \"bizarre code\" \nin: unknownLanguage";
        String options = "# h=1,2";
        String output = codeTag.parse(string, options);

        assertPygmentsRan(output);
    }

    @Test
    public void languageCodeTagShouldUnderstandLineNumbersAndCSharpLanguage() {
        String string = "public class SomeClass {";
        String options = "c# #";
        String output = codeTag.parse(string, options);

        assertPygmentsRan(output);
    }

    @Test
    public void codeTagWithReferenceWithoutLanguage() throws Exception {
        String options = "label=javacode1";
        String code = "class Main {\n" + "public static void main(String[] args) {\n"
                + "System.out.println(\"Hello world\");\n" + "}\n}";
        String output = codeTag.parse(code, options);

        assertTrue(output.startsWith("\\tubainaCodeLabel{javacode1}"));
        assertPygmentsRan(output);
    }

    @Test
    public void codeTagWithReferenceWithLanguage() throws Exception {
        String options = "java label=javacode1";
        String code = "class Main {\n" + "public static void main(String[] args) {\n"
                + "System.out.println(\"Hello world\");\n" + "}\n}";
        String output = codeTag.parse(code, options);

        assertTrue(output.startsWith("\\tubainaCodeLabel{javacode1}"));
        assertPygmentsRan(output);
    }

    @Test
    public void codeTagWithFileNameWithoutLanguage() throws Exception {
        String options = "filename=src/Main.java";
        String code = "class Main {\n" + "public static void main(String[] args) {\n"
                + "System.out.println(\"Hello world\");\n" + "}\n}";
        String output = codeTag.parse(code, options);

        assertTrue(output.startsWith("\\tubainaCodeFileName{src/Main.java}\n"));
        assertPygmentsRan(output);
    }

    @Test
    public void codeTagWithFileNameWithLanguage() throws Exception {
        String options = "java filename=src/Main.java";
        String code = "class Main {\n" + "public static void main(String[] args) {\n"
                + "System.out.println(\"Hello world\");\n" + "}\n}";
        String output = codeTag.parse(code, options);

        assertTrue(output.startsWith("\\tubainaCodeFileName{src/Main.java}\n"));
        assertPygmentsRan(output);
    }

    @Test
    public void codeTagWithFileNameWithLanguageAndLabel() throws Exception {
        String options = "java filename=src/Main2.java label=javacode1";
        String code = "class Main {\n" + "public static void main(String[] args) {\n"
                + "System.out.println(\"Hello world\");\n" + "}\n}";
        String output = codeTag.parse(code, options);

        assertTrue(output.startsWith("\\tubainaCodeLabel{javacode1}\n"));
        assertPygmentsRan(output);
    }

    @Test
    public void javascriptLangBug() throws Exception {
        String options = "javascript";
        String code = "writeTotal(3.14159);";
        String output = codeTag.parse(code, options);

        assertFalse(output.contains("javascript"));
        assertPygmentsRan(output);
    }

    private void assertPygmentsRan(String output) {
        assertTrue(output.contains("\\begin{Verbatim}[commandchars="));
        assertTrue(output.contains("\\end{Verbatim}"));
    }
    // TODO: file name as an option to code
}
