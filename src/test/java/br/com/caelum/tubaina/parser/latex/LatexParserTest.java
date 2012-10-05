package br.com.caelum.tubaina.parser.latex;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.Tag;

public class LatexParserTest {

    private LatexParser parser;

    @Before
    public void setUp() throws IOException {
        RegexConfigurator configurator = new RegexConfigurator();
        List<Tag> tags = configurator.read("/regex.properties", "/latex.properties");

        this.parser = new LatexParser(tags);
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
    public void testParseParagraph() {
        Assert.assertEquals("\n\nola mundo", parser.parseParagraph("ola mundo"));
        Assert.assertEquals("\n\n\\definition{test}", parser.parseParagraph("**test**"));
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

    // /////////////// missing BOX TAG tests ////////////////////

    @Test
    public void testTagSoloTag() {
        String result = parser.parseIndex("ola mundo");
        Assert.assertEquals("\n\\index{ola mundo}\n", result);
    }

    @Test
    public void testTagMultiTag() {
        String result = parser.parseIndex("ola mundo, olamundo");
        Assert.assertEquals("\n\\index{ola mundo, olamundo}\n", result);
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
    public void testItemSplittBug() {
        String input = "* Refactoring, Martin Fowler\n\n"
                + "* Effective Java, Joshua Bloch\n\n* Design Patterns, Erich Gamma et al";
        List<Chunk> chunks = new ChunkSplitter(null, "list").splitChunks(input);
        Assert.assertEquals(3, chunks.size());
        Assert.assertEquals("\n\\item{Refactoring, Martin Fowler}\n", chunks.get(0).getContent(
                parser));
        Assert.assertEquals("\n\\item{Effective Java, Joshua Bloch}\n", chunks.get(1).getContent(
                parser));
        Assert.assertEquals("\n\\item{Design Patterns, Erich Gamma et al}\n", chunks.get(2)
                .getContent(parser));
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