package br.com.caelum.tubaina.parser.html.desktop;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import br.com.caelum.tubaina.util.CommandExecutor;

public class HtmlCodeHighlighterTest {
    
    @Test
    public void shouldCallPygments() throws Exception {
        CommandExecutor executor = mock(CommandExecutor.class);
        String code = "public class Foo {\n" +
                "public int Bar(){\n" +
                "return 0;\n" +
                "}\n" +
                "}";
        HtmlCodeHighlighter highlighter = new HtmlCodeHighlighter(executor);
        highlighter.highlight(code);
        verify(executor).execute(eq("pygmentize"), anyString());
    }
}
