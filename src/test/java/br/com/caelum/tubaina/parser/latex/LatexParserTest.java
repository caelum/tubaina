package br.com.caelum.tubaina.parser.latex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.ParseType;
import br.com.caelum.tubaina.parser.Parser;

//TODO: make paragraph with bold, boxes with inner chunks, for example, somewhere else.
public class LatexParserTest {

    private Parser parser;

    @Before
    public void setUp() throws IOException {
        this.parser = ParseType.LATEX.getParser();
    }

    @Test
    public void testBoldTagInline() {
        String result = parser.parse("ola **mundo**");
        Assert.assertEquals("ola \\definition{mundo}", result);
    }

    @Test
    public void testBoldTagMultiline() {
        String result = parser.parse("ola **mu\nndo**");
        Assert.assertEquals("ola \\definition{mu\nndo}", result);
    }

    @Test
    public void testItalicTagInline() {
        String result = parser.parse("ola ::mundo::");
        Assert.assertEquals("ola \\italic{mundo}", result);
    }

    @Test
    public void testItalicTagMultiline() {
        String result = parser.parse("ola ::mu\nndo::");
        Assert.assertEquals("ola \\italic{mu\nndo}", result);
    }

    @Test
    public void testUnderlineTagInline() {
        String result = parser.parse("ola __mundo__");
        Assert.assertEquals("ola \\underlined{mundo}", result);
    }

    @Test
    public void testUnderlineTagMultiline() {
        String result = parser.parse("ola __mu\nndo__");
        Assert.assertEquals("ola \\underlined{mu\nndo}", result);
    }

    @Test
    public void testInlineCodeTagInline() {
        String result = parser.parse("ola %%mundo%%");
        Assert.assertEquals("ola \\codechunk{mundo}", result);
    }

    @Test
    public void testInlineCodeTagMultiline() {
        String result = parser.parse("ola %%mu\nndo%%");
        Assert.assertEquals("ola \\codechunk{mu\nndo}", result);
    }

    // Test for inline Ruby code with inheritance symbol (::)
    @Test
    public void testTwoInlineCodeTagsWithTwoColonsInside() {
        String result = parser
                .parse("ola %%mundo <:: superclasse%% texto %%mais codigo <:: superclasse%%");
        Assert.assertEquals(
                "ola \\codechunk{mundo \\char58\\char58 superclasse} texto \\codechunk{mais codigo \\char58\\char58 superclasse}",
                result);
    }

    @Test
    public void testQuotationTagInline() {
        String result = parser.parse("[quote ola mundo --Anonimo]");
        Assert.assertEquals("\\chapterquote{ola mundo}{Anonimo}", result);
    }

    @Test
    public void testQuotationTagMultiline() {
        String result = parser.parse("[quote ola mu\nndo -- Anonimo]");
        Assert.assertEquals("\\chapterquote{ola mu\nndo}{Anonimo}", result);
    }

    @Test
    public void testLabelTagInline() {
        String result = parser.parse("[footnote ola mundo]HelloWorld[/footnote]");
        Assert.assertEquals("HelloWorld\\footnote{ola mundo}", result);
    }

    @Test
    public void testLabelTagMultiline() {
        String result = parser.parse("[footnote ola \nmundo]Hello\nWorld[/footnote]");
        Assert.assertEquals("Hello\nWorld\\footnote{ola \nmundo}", result);
    }

    @Test
    public void testMailTagInline() {
        String result = parser.parse("[mail]olamundo@caelum.com.br[/mail]");
        Assert.assertEquals("\\email{olamundo}{caelum.com.br}", result);
    }

    @Test
    public void testMailTagWithUnderscoreInline() {
        String result = parser.parse("[mail]ola_mundo@caelum.com.br[/mail]");
        Assert.assertEquals("\\email{ola\\_mundo}{caelum.com.br}", result);
    }

    @Test
    public void testTitleTagInline() {
        String result = parser.parse("[title ola mundo]");
        Assert.assertEquals("\\sectiontitle{ola mundo}", result);
    }

    @Test
    public void testTitleTagInlineWithInnerTags() {
        String result = parser.parse("[title ola **mu__n__do**]");
        Assert.assertEquals("\\sectiontitle{ola \\definition{mu\\underlined{n}do}}", result);
    }

    @Test
    public void testQuotationTag() {
        String result = parser.parse("\"\"");
        Assert.assertEquals("``''", result);
    }

    @Test
    public void testQuotationTagWithText() {
        String result = parser.parse("\"qualquer coisa escrito aqui\"");
        Assert.assertEquals("``qualquer coisa escrito aqui''", result);
    }

    @Test
    public void testQuotationTagWithTextMultiline() {
        String result = parser.parse("     \"qualquer coisa escrito aqui\"   \n"
                + "(\"blah\") {'blah'}\n");
        Assert.assertEquals("     ``qualquer coisa escrito aqui''   \n"
                + "(``blah'') \\{`blah'\\}\n", result);
    }

    @Test
    public void testEscapeSpecialChars() {
        String result = parser.parse("$ \\ _ ~ % # ^ & { }");
        Assert.assertEquals("\\$ \\char92 \\_ \\char126 \\% \\# \\char94 \\& \\{ \\}", result);
    }

    @Test
    public void testQuoteInsideCodeInline() {
        String input = "%%String s = \"string\"%%";
        String output = parser.parse(input);
        Assert.assertEquals("\\codechunk{String s = \\textquotedbl string\\textquotedbl }", output);
    }
    
    @Test
    public void testQuoteInsideJavaCodeInline() {
        String input = "%%object.do(\"some string\", 'c');%%";
        String output = parser.parse(input);
        Assert.assertEquals(
                "\\codechunk{object.do(\\textquotedbl some string\\textquotedbl , \\textquotesingle c\\textquotesingle );}",
                output);
    }

}