package br.com.caelum.tubaina.parser.html.desktop;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.util.CommandExecutor;

public class SyntaxHighlighterTest {

    private String sampleCode;
    private CommandExecutor executor;
    private SyntaxHighlighter highlighter;
    private CodeCache codeCache;

    @Before
    public void setUp() {
        this.sampleCode = "public class Foo {\n" + "public int Bar(){\n" + "return 0;\n" + "}\n" + "}";
        this.executor = mock(CommandExecutor.class);
        this.codeCache = mock(CodeCache.class);
        this.highlighter = new SyntaxHighlighter(executor, SyntaxHighlighter.HTML_OUTPUT, false, codeCache);
    }

    @Test
    public void shouldCallPygmentsWithJavaLexer() throws Exception {
        String code = "public class Foo {\n" + "public int Bar(){\n" + "return 0;\n" + "}\n" + "}";
        highlighter.highlight(code, "java", false);
        String encoding = System.getProperty("file.encoding");
        List<String> arguments = Arrays.asList("pygmentize", "-O", "encoding=" + encoding
                + ",outencoding=UTF-8", "-f", "html", "-l", "java");
        verify(executor).execute(eq(arguments), eq(sampleCode));
    }

    @Test
    public void shouldCallPygmentsWithNumberedLinesOption() throws Exception {
        highlighter.highlight(sampleCode, "java", true);
        String encoding = System.getProperty("file.encoding");
        List<String> arguments = Arrays.asList("pygmentize", "-O", "encoding=" + encoding
                + ",outencoding=UTF-8,linenos=inline", "-f", "html", "-l", "java");
        verify(executor).execute(eq(arguments), eq(sampleCode));
    }

    @Test
    public void shouldCallPygmentsWithHlLines() throws Exception {

        List<Integer> lines = Arrays.asList(1, 2, 5);

        highlighter.highlight(sampleCode, "java", false, lines);

        String encoding = System.getProperty("file.encoding");

        List<String> arguments = Arrays.asList("pygmentize", "-O", "encoding=" + encoding
                + ",outencoding=UTF-8,hl_lines=1 2 5", "-f", "html", "-l", "java");

        verify(executor).execute(eq(arguments), eq(sampleCode));
    }
    
    @Test
    public void shouldCallPygmentsWithLatexOutput() throws Exception {
        this.highlighter = new SyntaxHighlighter(executor, SyntaxHighlighter.LATEX_OUTPUT, false, codeCache);
        highlighter.highlight(sampleCode, "java", false);
        String encoding = System.getProperty("file.encoding");
        List<String> arguments = Arrays.asList("pygmentize", "-O", "encoding=" + encoding
                + ",outencoding=UTF-8", "-P", "verboptions=numbersep=5pt", "-f", "latex", "-l", "java");
        verify(executor).execute(eq(arguments), eq(sampleCode));
    }

}
