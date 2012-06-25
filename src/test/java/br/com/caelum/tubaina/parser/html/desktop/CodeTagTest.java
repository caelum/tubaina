package br.com.caelum.tubaina.parser.html.desktop;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;

public class CodeTagTest {

    private String code;

    @Before
    public void setUp() {
        code = "public static void main(String[] args) {\n" + "	String name = \"Gabriel\";\n"
                + "	System.out.println(\"Hello, \" + name);\n" + "}";
    }

    @Test
    public void plainJavaCode() throws Exception {
        String options = "java";
        HtmlCodeHighlighter htmlCodeHighlighter = mock(HtmlCodeHighlighter.class);
        CodeTag codeTag = new CodeTag(htmlCodeHighlighter);
        codeTag.parse(code, options);
        verify(htmlCodeHighlighter).highlight(eq(code), eq(options), eq(false));
    }

    @Test
    public void javaCodeWithNumberedLines() throws Exception {
        String options = "java #";
        HtmlCodeHighlighter htmlCodeHighlighter = mock(HtmlCodeHighlighter.class);
        CodeTag codeTag = new CodeTag(htmlCodeHighlighter);
        codeTag.parse(code, options);
        verify(htmlCodeHighlighter).highlight(eq(code), eq("java"), eq(true));
    }

    @Test
    public void plainRubyCode() throws Exception {
        String options = "ruby";
        String rubyCode = "@name = \"Gabriel\"\n" + "puts \"Hello, \" + name";
        HtmlCodeHighlighter htmlCodeHighlighter = mock(HtmlCodeHighlighter.class);
        CodeTag codeTag = new CodeTag(htmlCodeHighlighter);
        codeTag.parse(rubyCode, options);
        verify(htmlCodeHighlighter).highlight(eq(rubyCode), eq("ruby"), eq(false));
    }

    @Test
    public void noLanguageDefinedIsTreatedAsText() throws Exception {
        String options = "";
        String noParticularLanguage = "Some text explaining some new bizarre\n"
                + "syntax in a very code alike way";
        HtmlCodeHighlighter htmlCodeHighlighter = mock(HtmlCodeHighlighter.class);
        CodeTag codeTag = new CodeTag(htmlCodeHighlighter);
        String output = codeTag.parse(noParticularLanguage, options);
        verify(htmlCodeHighlighter).highlight(eq(noParticularLanguage), eq("text"), eq(false));
    }

    @Test
    public void noLanguageDefinedIsTreatedAsTextEvenWhenItIsNumbered() throws Exception {
        String options = "#";
        String noParticularLanguage = "Some text explaining some new bizarre\n"
                + "syntax in a very code alike way";
        HtmlCodeHighlighter htmlCodeHighlighter = mock(HtmlCodeHighlighter.class);
        CodeTag codeTag = new CodeTag(htmlCodeHighlighter);
        String output = codeTag.parse(noParticularLanguage, options);
        verify(htmlCodeHighlighter).highlight(eq(noParticularLanguage), eq("text"), eq(true));
    }

    @Test
    public void shouldThrowExpectionWhenTagContainsLabel() {
        try {
            new CodeTag().parse("", "label=somelabel");
            Assert.fail("should throw excpetion");
        } catch (TubainaException e) {
        }
    }
    
    @Test
    public void shouldThrowExpectionWhenOptionsContainsHighlight() {
        try {
            new CodeTag().parse("", "h=1");
            Assert.fail("should throw excpetion");
        } catch (TubainaException e) {
        }
    }
}
