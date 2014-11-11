package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag;
import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.CodeChunk;

import static br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag.BEGIN_END;
import static br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag.BEGIN_START;
import static br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag.END;

public class CodeTagTest extends AbstractTagTest {

	@Test
	public void notNumberedGenericCode() {
		String code = "public static void main(String[] args) {\n" + "    String name = \"Gabriel\";\n"
				+ "    System.out.println(\"Hello, \" + name);\n" + "}";
		CodeChunk chunk = new CodeChunk(code, "");
		String result = getContent(chunk);
		Assert.assertEquals(BEGIN_START + BEGIN_END + "<div class=\"highlight\">" +
							"<pre>public static void main(String[] args) {" + 
							"\n    String name = &quot;Gabriel&quot;;" + 
							"\n    System.out.println(&quot;Hello, &quot; + name);" + 
							"\n}" + 
							"\n</pre>" + 
							"</div>" + END, result);
	}

	@Test
	public void numberedGenericCode() {
		String code = "public static void main(String[] args) {\n" + "    String name = \"Gabriel\";\n"
				+ "    System.out.println(\"Hello, \" + name);\n" + "}";
		CodeChunk chunk = new CodeChunk(code, "#");
		String result = getContent(chunk);
		Assert.assertEquals(BEGIN_START + BEGIN_END + "<div class=\"highlight\">" +
							"<pre><span class=\"lineno\">1</span> " + 
							"public static void main(String[] args) {" + 
							"\n<span class=\"lineno\">2</span> " + 
							"    String name = &quot;Gabriel&quot;;" + 
							"\n<span class=\"lineno\">3</span> " + 
							"    System.out.println(&quot;Hello, &quot; + name);" + 
							"\n<span class=\"lineno\">4</span>" + 
							" }" + 
							"\n</pre>" + 
							"</div>" + END, result);
	}
}
