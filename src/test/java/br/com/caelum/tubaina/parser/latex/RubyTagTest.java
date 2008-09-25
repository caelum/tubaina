package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;

public class RubyTagTest {
	private final String BEGIN = "{\n" + "\\small \\noindent \\ttfamily \n";
	private final String END = "\n}\n";
	private final RubyTag rubyTag = new RubyTag(new SimpleIndentator());

	@Test
	public void testComments() {
		String code = "# this is a ruby comment\n";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubycomment \\#~this~is~a~ruby~comment"
				+ END, result);
		code = "=begin\nThis is a\nmultiline comment\n=end";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubycomment \\verb#=#begin\\\\\nThis~is~a\\\\\nmultiline~comment\\\\\n\\verb#=#end"
				+ END, result);
	}

	@Test
	public void testSingleQuotedString() {
		String code = "\"this is a double quoted string\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubystring \\verb#\"#this~is~a~double~quoted~string\\verb#\"#"
				+ END, result);
	}

	@Test
	public void testDoubleQuotedString() {
		String code;
		String result;
		code = "'this is a single quoted string'";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubystring \\verb#'#this~is~a~single~quoted~string\\verb#'#"
				+ END, result);
	}

	@Test
	public void testGeneralizedSingleQuotedString() {
		String code;
		String result;
		code = "%q!I said, \"You said, 'She said it.'\"!";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubystring \\%q!I~said,~\\verb#\"#You~said,~\\verb#'#She~said~it.\\verb#'#\\verb#\"#!"
				+ END, result);
	}

	@Test
	public void testGeneralizedDoubleQuotedString() {
		String code;
		String result;
		code = "%Q/I said, \"You said, 'She said it.'\"/";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubystring \\%Q\\verb#/#I~said,~\\verb#\"#You~said,~\\verb#'#She~said~it.\\verb#'#\\verb#\"#\\verb#/#"
				+ END, result);
		code = "%(I said, \"You said, 'She said it.'\")";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubystring \\%(I~said,~\\verb#\"#You~said,~\\verb#'#She~said~it.\\verb#'#\\verb#\"#)"
				+ END, result);
	}

	@Test
	public void testQuotedTextInsideCommentIsNotString() {
		String code = "# this is a comment with 'quoted' and \"double quoted\" text";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubycomment \\#~this~is~a~comment~with~\\verb#'#quoted\\verb#'#~and~\\verb#\"#double~quoted\\verb#\"#~text"
				+ END, result);
	}

	@Test
	public void testMultipleStringsInSameLine() {
		String code = "method 'a string', \"another string\", %q/yet another string/";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "method~\\rubystring \\verb#'#a~string\\verb#'#,~\\rubystring \\verb#\"#another~string\\verb#\"#,~\\rubystring \\%q\\verb#/#yet~another~string\\verb#/#"
				+ END, result);
	}

	@Test
	public void testDoubleQuotedTextInsideSingleQuotedString() {
		String code = "'this is a \"single-quoted\" string'";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubystring \\verb#'#this~is~a~\\verb#\"#single\\verb#-#quoted\\verb#\"#~string\\verb#'#"
				+ END, result);
	}
	
	@Test
	public void testLineOrientedStringLiteral() {
		String code = "puts <<BLAH\nThis is a\nmultiline, line-oriented\nstring\nBLAH";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "puts~\\rubystring \\verb#<#\\verb#<#BLAH\\\\\nThis~is~a\\\\\nmultiline,~line\\verb#-#oriented\\\\\nstring\\\\\nBLAH"
				+ END, result);
		code = "<<`CODE`\necho \"hello\"\necho \"world\"\nCODE";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubystring \\verb#<#\\verb#<#`CODE`\\\\\necho~\\verb#\"#hello\\verb#\"#\\\\\necho~\\verb#\"#world\\verb#\"#\\\\\nCODE"
				+ END, result);
	}

	@Test
	public void testSingleQuotedTextInsideDoubleQuotedString() {
		String code = "puts \"He said: 'Hello World!'\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "puts~\\rubystring \\verb#\"#He~said:~\\verb#'#Hello~World!\\verb#'#\\verb#\"#"
				+ END, result);
	}

	@Test
	public void testReservedWords() {
		String code = "BEGIN class ensure nil self when END def false not super while alias defined for or then yield and do if redo true begin else in rescue undef break elsif module retry unless case end next return until raise defined?";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "\\rubykeyword BEGIN~\\rubykeyword class~"
				+ "\\rubykeyword ensure~\\rubykeyword nil~"
				+ "\\rubykeyword self~\\rubykeyword when~"
				+ "\\rubykeyword END~\\rubykeyword def~"
				+ "\\rubykeyword false~\\rubykeyword not~"
				+ "\\rubykeyword super~\\rubykeyword while~"
				+ "\\rubykeyword alias~\\rubykeyword defined~"
				+ "\\rubykeyword for~\\rubykeyword or~"
				+ "\\rubykeyword then~\\rubykeyword yield~"
				+ "\\rubykeyword and~\\rubykeyword do~"
				+ "\\rubykeyword if~\\rubykeyword redo~"
				+ "\\rubykeyword true~\\rubykeyword begin~"
				+ "\\rubykeyword else~\\rubykeyword in~"
				+ "\\rubykeyword rescue~\\rubykeyword undef~"
				+ "\\rubykeyword break~\\rubykeyword elsif~"
				+ "\\rubykeyword module~\\rubykeyword retry~"
				+ "\\rubykeyword unless~\\rubykeyword case~"
				+ "\\rubykeyword end~\\rubykeyword next~"
				+ "\\rubykeyword return~\\rubykeyword until~"
				+ "\\rubykeyword raise~\\rubykeyword defined?"
				+ END, result);
	}
	
	@Test
	public void testRegularExpressions() {
		String code = "/regexp/";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "\\rubyregexp \\verb#/#regexp\\verb#/#" + END, result);
		code = "%r(another/regexp/)";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "\\rubyregexp \\%r(another\\verb#/#regexp\\verb#/#)" + END, result);
		code = "/ReGeXp/i";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "\\rubyregexp \\verb#/#ReGeXp\\verb#/#i" + END, result);
	}
	
	@Test
	public void testVariables() {
		String code = "@local = nil";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "\\rubyvariable \\verb#@#local~\\verb#=#~\\rubykeyword nil" + END, result);
		code = "@@count = 1";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "\\rubyvariable \\verb#@#\\verb#@#count~\\verb#=#~1" + END, result);
		code = "$counter++";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "\\rubyvariable \\$counter++" + END, result);
	}
	
	@Test
	public void testConstant() {
		String code = "PI = 3.14159";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "\\rubyconstant PI~\\verb#=#~3.14159" + END, result);
	}
}
