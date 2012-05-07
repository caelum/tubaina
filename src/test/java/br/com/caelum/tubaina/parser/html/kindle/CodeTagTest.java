package br.com.caelum.tubaina.parser.html.kindle;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.kindle.CodeTag;


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
		Assert.assertEquals("<pre class=\"java\">\n" + code + "\n</pre>", output);
	}
	
	@Test
	public void plainRubyCode() throws Exception {
		String options = "ruby";
		String rubyCode = "@name = \"Gabriel\"\n" +
						  "puts \"Hello, \" + name";
		String output = new CodeTag().parse(rubyCode, options);
		Assert.assertEquals("<pre class=\"ruby\">\n" + rubyCode + "\n</pre>", output);
	}
	
	@Test
	public void noLanguageDefinedIsTreatedAsText() throws Exception {
		String options="";
		String noParticularLanguage = "Some text explaining some new bizarre\n" +
										"syntax in a very code alike way";
		String output = new CodeTag().parse(noParticularLanguage, options);
		Assert.assertEquals("<pre class=\"text\">\n" 
													+ noParticularLanguage + "\n</pre>", output);
	}
}
