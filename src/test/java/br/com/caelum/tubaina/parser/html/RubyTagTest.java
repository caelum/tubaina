package br.com.caelum.tubaina.parser.html;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class RubyTagTest {
	private final String BEGIN = "<div class=\"ruby\"><code class=\"ruby\">\n";
	private final String END = "</code></div>\n";
	private final RubyTag rubyTag = new RubyTag(new SimpleIndentator());

	@Test
	public void testComments() {
		String code = "# this is a ruby comment";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +	comment(code) + END, result);
		code = "=begin\nThis is a\nmultiline comment\n=end";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + comment(code) + END, result);
	}

	@Test
	public void testSingleQuotedString() {
		String code = "\"this is a double quoted string\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
	}

	@Test
	public void testDoubleQuotedString() {
		String code = "'this is a single quoted string'";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
	}

	@Test
	public void testGeneralizedSingleQuotedString() {
		String code = "%q!I said, \"You said, 'She said it.'\"!";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
	}

	@Test
	public void testGeneralizedDoubleQuotedString() {
		String code;
		String result;
		code = "%Q/I said, \"You said, 'She said it.'\"/";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
		code = "%(I said, \"You said, 'She said it.'\")";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
	}

	@Test
	public void testQuotedTextInsideCommentIsNotString() {
		String code = "# this is a comment with 'quoted' and \"double quoted\" text";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + comment(code) + END, result);
	}

	@Test
	public void testMultipleStringsInSameLine() {
		String code = "method 'a string', \"another string\", %q/yet another string/";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("method ") +
				string("'a string'") +
				escape(", ") +
				string("\"another string\"") +
				escape(", ") +
				string("%q/yet another string/")
				+ END, result);
	}

	@Test
	public void testDoubleQuotedTextInsideSingleQuotedString() {
		String code = "'this is a \"single-quoted\" string'";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
	}

	@Test
	public void testLineOrientedStringLiteral() {
		String code = "puts <<BLAH\nThis is a\nmultiline, line-oriented\nstring\nBLAH\n";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("puts ") +
				string("<<BLAH") +
				escape("\n") +
				string("This is a\n" +
						"multiline, line-oriented\n" +
						"string\n" +
						"BLAH"
				) +
				escape("\n") +
				END, result);
		code = "<<`CODE`\necho \"hello\"\necho \"world\"\nCODE\n";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				execution("<<`CODE`") +
				escape("\n") +
				execution("echo \"hello\"\n" +
						"echo \"world\"\n" +
						"CODE"
				) +
				escape("\n") +
				END, result);
	}

	@Test
	public void testSingleQuotedTextInsideDoubleQuotedString() {
		String code = "puts \"He said: 'Hello World!'\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("puts ") +
				string("\"He said: 'Hello World!'\"") +
				END, result);
	}

	@Test
	public void testReservedWords() {
		String[] keywords = {
				"BEGIN", "class", "ensure", "nil", "self",
				"when", "END", "def", "false", "not",
				"super", "while", "alias", "defined", "for",
				"or", "then", "yield", "and", "do",
				"if", "redo", "true", "begin", "else",
				"in", "rescue", "undef", "break", "elsif",
				"module", "retry", "unless", "case", "end",
				"next", "return", "until", "raise", "defined?"};
		for (String keyword : keywords) {
			String result = rubyTag.parse(keyword, "");
			Assert.assertEquals(BEGIN + keyword(keyword) + END, result);
		}
	}
	
	@Test
	public void testRegularExpressions() {
		String code = "/regexp/";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + regex(code) + END, result);
		code = "%r(another/regexp/)";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + regex(code) + END, result);
		code = "/ReGeXp/i";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + regex(code) + END, result);
	}
	
	@Test
	public void testVariables() {
		String code = "@local = nil";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				variable("@local") +
				escape(" = ") +
				keyword("nil") +
				END, result);
		code = "@@count = 1";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				variable("@@count") +
				escape(" = ") +
				number("1") + 
				END, result);
		code = "$counter++";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				variable("$counter") + "++" +
				END, result);
	}
	
	@Test
	public void testConstant() {
		String code = "PI = 3.14159";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				constant("PI") +
				escape(" = ") +
				number("3.14159") +
				END, result);
	}
	
	@Test
	public void testNumbers() {
		String code = "12345";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + number(code) + END, result);
		code = "123.45";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + number(code) + END, result);
		code = "123e45";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + number(code) + END, result);
		code = "123E-45";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + number(code) + END, result);
		code = "-123.45e67";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + number(code) + END, result);
		code = "0xffffff";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + number(code) + END, result);
		code = "0b010010";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + number(code) + END, result);
		code = "01777";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + number(code) + END, result);
	}
	
	@Test
	public void testSymbols() {
		String code = "attr_accessor :name, :simple_symbol";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("attr_accessor ") +
				symbol(":name") + 
				escape(", ") +
				symbol(":simple_symbol") +
				END, result);
		code = "%s(strange_symbol)";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + symbol(code) + END, result);
	}
	
	@Test
	public void testMultipleLineCode() throws IOException {
		ResourceLocator.initialize(".");
		String code = readFile("/src/test/resources/test_ruby_color.rb");
		String result = rubyTag.parse(code, ""); 
		String expected = readFile("/src/test/resources/test_ruby_color.html");
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testKeywordMethodCalledOnAString() {
		String code = "defined?(\"text\") # should return true";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				keyword("defined?") + "(" +
				string("\"text\"") + ")" +
				escape(" ") +
				comment("# should return true") +
				END, result);
	}
	
	@Test
	public void testHashAndArrayKeys() {
		String code = "@params[:id]";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				variable("@params") + "[" +
				symbol(":id") + "]" +
				END, result);
		code = "$instances[5]";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				variable("$instances") + "[" +
				number("5") + "]" +
				END, result);
		code = "dict['word']";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("dict[") +
				string("'word'") + "]" +
				END, result);
	}
	
	@Test
	public void testMultipleNamespaces() {
		String code = "class User < ActiveRecord::Base";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				keyword("class") +
				escape(" ") +
				constant("User") +
				escape(" < ") +
				constant("ActiveRecord") +
				escape("::") +
				constant("Base") +
				END, result);
	}
	
	@Test
	public void testNumbersInRangeWithoutSpacesBetween() {
		String code = "array = [1,2,3,4]";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("array = [") +
				number("1") + "," +
				number("2") + "," +
				number("3") + "," +
				number("4") + "]" +
				END, result);
		code = "array = [1..4]";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				escape("array = [") +
				number("1") + ".." +
				number("4") + "]" +
				END, result);
	}
	
	@Test
	public void testEscapedCharactersInDoubleQuotedStrings() {
		String code = "\"this is a \\\"string\\\"\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
	}
	
	@Test
	public void testEscapedCharactersInSingleQuotedStrings() {
		String code = "'this is a \\'string\\''";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
	}
	
	@Test
	public void testEscapedCharactersInRegularExpressions() {
		String code = "/\\/.*\\//";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + regex(code) + END, result);
	}
	
	@Test
	public void testEscapedCharactersInGeneralizedStringsAndRegularExpressions() {
		String code = "%(this is a \\(generalized\\) string)";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
	}
	
	@Test
	public void testKeywordInsideIdentifierIsNotKeyword() {
		String code = "Session.expects(:open).once.and_return(s)";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				constant("Session") +
				escape(".expects(") +
				symbol(":open") +
				escape(").once.and_return(s)") +
				END, result);
	}
	
	@Test
	public void testArithmeticExpression() {
		String code = "print \"I'm crazy!\" if (3+4)-2++10.8 = (3*2)/4";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("print ") +
				string("\"I'm crazy!\"") +
				escape(" ") +
				keyword("if") +
				escape(" (") +
				number("3") +
				escape("+") +
				number("4") +
				escape(")-") +
				number("2") +
				escape("+") +
				number("+10.8") +
				escape(" = (") +
				number("3") +
				escape("*") +
				number("2") +
				escape(")/") +
				number("4") +
				END, result);
	}
	
	@Test
	public void testInterpolationInDoubleQuotedStringHasDifferentColor() {
		String code = "\"string with #{%Q!string with #{i+1+1} interpolation!}\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				string("\"string with #{}\"",
						string("%Q!string with #{} interpolation!",
								"i+" +
								number("1") +
								escape("+") +
								number("1")
						)
				) +
				END, result);
	}
	
	@Test
	public void testMultipleInterpolationsInsideDoubleQuotedString() {
		String code = "%/Today is #{@date.month} #{@date.day}#{suffixForDay(@date.day)}/";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				string("%/Today is #{} #{}#{}/",
						variable("@date") + escape(".month"),
						variable("@date") + escape(".day"),
						escape("suffixForDay(") + variable("@date") + escape(".day)")
				) +
				END, result);
	}
	
	@Test
	public void testLineOrientedStringWithMultipleIdentifiers() {
		String code = "print <<EXP1, <<'CMD', <<EXP2, <<`RESULT`\n" +
				"#{params[:user].name}, write the code:\n" +
				"EXP1\n" +
				"`ls #{dir}`\n" +
				"CMD\n" +
				"to have the following result:\n" +
				"EXP2\n" +
				"ls #{dir}\n" +
				"RESULT\n";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				escape("print ") +
				string("<<EXP1, ") +
				string("<<'CMD', ") +
				string("<<EXP2, ") +
				execution("<<`RESULT`") +
				escape("\n") +
				string("#{}, write the code:\n" +
						"EXP1",
						escape("params[") +
						symbol(":user") +
						escape("].name")
				) +
				escape("\n") +
				string("`ls #{dir}`\n" +
						"CMD"
				) +
				escape("\n") +
				string("to have the following result:\n" +
						"EXP2"
				) +
				escape("\n") +
				execution("ls #{}\n" +
						"RESULT",
						escape("dir")
				) +
				escape("\n") +
				END, result);
	}
	
	@Test
	public void testCommentsAmongTextBug() {
		String code = "print a.name\n# a comment\nb = 10";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("print a.name\n") +
				comment("# a comment") +
				escape("\nb = ") + number("10") +
				END, result);
	}
	
	@Test
	public void testHtmlLineBreakCodeInsideComment() {
		String code = "# to break a line, use <br/>\noutput << \"<br/>\\n\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				comment("# to break a line, use <br/>") +
				escape("\noutput << ") +
				string("\"<br/>\\n\"") +
				END, result);	
	}
	
	@Test
	public void testGraveQuotedString() {
		String code = "nfiles = `ls | wc -l`";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("nfiles = ") +
				execution("`ls | wc -l`") +
				END, result);
	}
	
	@Test
	public void testGraveQuotedStringWithInterpolations() {
		String code = "nfiles = `ls #{@user.home} | wc -l`";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("nfiles = ") +
				execution("`ls #{} | wc -l`",
						variable("@user") +
						escape(".home")
				) +
				END, result);
	}
	
	private String readFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(ResourceLocator.getInstance().getFile(filename)));
		String line;
		String content = "";
		while ((line = reader.readLine()) != null) {
			content += line + "\n";
		}
		reader.close();
		return content;
	}
	
	private String comment(String text) {
		return span("rubycomment", text);
	}
	
	private String string(String content, String... interpolations) {
		String span = span("rubystring", content);
		span = insertInterpolations(span, interpolations);
		return span;
	}
	
	private String variable(String name) {
		return span("rubyvariable", name);
	}
	
	private String regex(String regex) {
		return span("rubyregexp", regex);
	}
	
	private String keyword(String word) {
		return span("rubykeyword", word);
	}
	
	private String number(String number) {
		return span("rubynumber", number);
	}
	
	private String constant(String name) {
		return span("rubyconstant", name);
	}
	
	private String symbol(String name) {
		return span("rubysymbol", name);
	}
	
	private String execution(String code, String... interpolations) {
		String span = span("rubyexecution", code);
		span = insertInterpolations(span, interpolations);
		return span;
	}
	
	private String insertInterpolations(String span, String... interpolations) {
		for (String code : interpolations) {
			span = span.replaceFirst("#\\{\\}", "#{<span class=\"rubynormal\">" + code + "</span>}");
		}
		return span;
	}
	
	private String span(String clazz, String content) {
		return "<span class=\"" + clazz + "\">" + escape(content) + "</span>";
	}
	
	private String escape(String text) {
		text = text.replaceAll(" ", "&nbsp;");
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");
		text = text.replaceAll("\n", "<br/>\n");
		return text;
	}
}
