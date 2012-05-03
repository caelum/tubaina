package br.com.caelum.tubaina.parser.html.desktop;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.desktop.CodeTag;


public class CodeTagTest {
	
	private String code;

	@Before
	public void setUp() {
		code =  "public static void main(String[] args) {\n" +
				"	String name = \"Gabriel\";\n" +
				"	System.out.println(\"Hello, \" + name);\n" +
				"}";
	}
	
	@Test
	public void plainJavaCode() throws Exception {
		String options = "java";
		String output = new CodeTag().parse(code, options);
		Assert.assertEquals("<pre class=\"code java\">\n" + code + "\n</pre>", output);
	}

	@Test
	public void javaCodeWithHighlights() throws Exception {
		String options = "java h=1,4\n";
		String output = new CodeTag().parse(code, options);
		Assert.assertEquals("<pre class=\"code java\" data-highlight=\"1,4\">\n" + code + "\n</pre>", output);
	}
	
	@Test
	public void javaCodeWithNumberedLines() throws Exception {
		String options="java #";
		String output = new CodeTag().parse(code, options);
		Assert.assertEquals("<pre class=\"code java numbered\">\n" + code + "\n</pre>", output);
	}
	
	@Test
	public void plainRubyCode() throws Exception {
		String options = "ruby";
		String rubyCode = "@name = \"Gabriel\"\n" +
						  "puts \"Hello, \" + name";
		String output = new CodeTag().parse(rubyCode, options);
		Assert.assertEquals("<pre class=\"code ruby\">\n" + rubyCode + "\n</pre>", output);
	}
	
	@Test
	public void noLanguageDefinedIsTreatedAsText() throws Exception {
		String options="";
		String noParticularLanguage = "Some text explaining some new bizarre\n" +
										"syntax in a very code alike way";
		String output = new CodeTag().parse(noParticularLanguage, options);
		Assert.assertEquals("<pre class=\"code text\">\n" 
													+ noParticularLanguage + "\n</pre>", output);
	}
	@Test
	public void noLanguageDefinedIsTreatedAsTextEvenWhenItIsNumbered() throws Exception {
		String options="#";
		String noParticularLanguage = "Some text explaining some new bizarre\n" +
										"syntax in a very code alike way";
		String output = new CodeTag().parse(noParticularLanguage, options);
		Assert.assertEquals("<pre class=\"code text numbered\">\n" 
				+ noParticularLanguage + "\n</pre>", output);
	}
	@Test
	public void noLanguageDefinedIsTreatedAsTextEvenWhenItHasHighlights() throws Exception {
		String options="h=2";
		String noParticularLanguage = "Some text explaining some new bizarre\n" +
										"syntax in a very code alike way";
		String output = new CodeTag().parse(noParticularLanguage, options);
		Assert.assertEquals("<pre class=\"code text\" data-highlight=\"2\">\n" 
				+ noParticularLanguage + "\n</pre>", output);
	}
}
