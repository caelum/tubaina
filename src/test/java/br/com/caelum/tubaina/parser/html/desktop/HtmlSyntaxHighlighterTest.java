package br.com.caelum.tubaina.parser.html.desktop;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.util.CommandExecutor;

public class HtmlSyntaxHighlighterTest {

    private String sampleCode;

    @Before
    public void setUp() {
        sampleCode = "public class Foo {\n" + "public int Bar(){\n" + "return 0;\n" + "}\n" + "}";
    }

    @Test
    public void shouldCallPygmentsWithJavaLexer() throws Exception {
        CommandExecutor executor = mock(CommandExecutor.class);
        String code = "public class Foo {\n" + "public int Bar(){\n" + "return 0;\n" + "}\n" + "}";
        HtmlSyntaxHighlighter highlighter = new HtmlSyntaxHighlighter(executor);
        highlighter.highlight(code, "java", false);
        verify(executor).execute(eq("pygmentize -f html -l java"), eq(sampleCode));
    }

    @Test
    public void shouldCallPygmentsWithNumberedLinesOption() throws Exception {
        CommandExecutor executor = mock(CommandExecutor.class);
        HtmlSyntaxHighlighter highlighter = new HtmlSyntaxHighlighter(executor);
        highlighter.highlight(sampleCode, "java", true);
        verify(executor).execute(eq("pygmentize -P lineos=inline -f html -l java"), eq(sampleCode));
    }

}
