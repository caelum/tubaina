package br.com.caelum.tubaina.parser.html.kindle;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag;
import br.com.caelum.tubaina.parser.html.kindle.CodeTag;


public class CodeTagTest {
	
	@Test
    public void shouldCallHtmlCodeTag() {
        String code = "public static void main(String[] args) {\n" +
                "    String name = \"Gabriel\";\n" +
                "    System.out.println(\"Hello, \" + name);\n" +
                "}";
        HtmlAndKindleCodeTag htmlCodeTag = mock(HtmlAndKindleCodeTag.class);
        CodeTag codeTag = new CodeTag(htmlCodeTag);
        codeTag.parse(code, "java");
        verify(htmlCodeTag).parse(eq(code), eq("java"));
    }
}
