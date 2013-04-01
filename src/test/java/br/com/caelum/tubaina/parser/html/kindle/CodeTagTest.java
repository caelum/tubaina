package br.com.caelum.tubaina.parser.html.kindle;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag;


public class CodeTagTest extends AbstractTagTest {
	
	@Test
    public void shouldCallHtmlCodeTag() {
        String code = "public static void main(String[] args) {\n" +
                "    String name = \"Gabriel\";\n" +
                "    System.out.println(\"Hello, \" + name);\n" +
                "}";
        HtmlAndKindleCodeTag htmlCodeTag = mock(HtmlAndKindleCodeTag.class);
        CodeChunk chunk = new CodeChunk(code, "");
        getContent(chunk);
        verify(htmlCodeTag).parse(chunk);
    }
}
