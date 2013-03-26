package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.CodeChunk;

public class CodeTagTest extends AbstractTagTest {

    @Test
    public void shouldCallHtmlCodeTag() {
        String code = "public static void main(String[] args) {\n" +
                "    String name = \"Gabriel\";\n" +
                "    System.out.println(\"Hello, \" + name);\n" +
                "}";
        CodeChunk chunk = new CodeChunk(code, "");
		String result = getContent(chunk);
		Assert.assertEquals("<pre ><div class=\"highlight\">" +
								"<pre><span class=\"lineno\">1</span> " +
								"public static void main(String[] args) {" +
								"\n<span class=\"lineno\">2</span> " +
								"    String name = &quot;Gabriel&quot;;" +
								"\n<span class=\"lineno\">3</span> " +
								"    System.out.println(&quot;Hello, &quot; + name);" +
								"\n<span class=\"lineno\">4</span>" +
								" }" +
								"\n</pre>" +
							"</div></pre>", result);
	}
}
