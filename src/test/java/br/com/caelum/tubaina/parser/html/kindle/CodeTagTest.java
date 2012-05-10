package br.com.caelum.tubaina.parser.html.kindle;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class CodeTagTest {
	
	private String code;

	@Before
	public void setUp() {
		code =  "public static void main(String[] args) {\n" +
				"	String name = \"Gabriel\";\n" +
				"	System.out.println(\"Hello, \" + name);\n" +
				"}";
		code = code.replaceAll(" ", "&nbsp;");
		code = code.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
	}
	@Test
	public void labelShouldBeShown() throws Exception {
		String options="label=bizarre-code";
		String noParticularLanguage = "Some text explaining some new bizarre\n" +
				"syntax in a very code alike way";
		noParticularLanguage = noParticularLanguage.replaceAll(" ", "&nbsp;");
		String output = new CodeTag().parse(noParticularLanguage, options);
		Assert.assertEquals("<pre id=\"bizarre-code\">\n" 
				+ noParticularLanguage + "\n</pre>", output);
	}
}
