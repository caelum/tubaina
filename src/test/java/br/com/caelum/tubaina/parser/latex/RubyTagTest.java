package br.com.caelum.tubaina.parser.latex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class RubyTagTest {
	private final String BEGIN = "{\n" + "\\small \\noindent \\ttfamily \n";
	private final String END = "\n}\n";
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
				string("<<BLAH\nThis is a\nmultiline, line-oriented\nstring\nBLAH\n") +
				END, result);
		code = "<<`CODE`\necho \"hello\"\necho \"world\"\nCODE\n";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + string(code) + END, result);
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
				variable("@local ") +
				escape("= ") +
				keyword("nil") +
				END, result);
		code = "@@count = 1";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				variable("@@count ") +
				escape("= ") +
				number("1") + 
				END, result);
		code = "$counter++";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				variable("$counter") +
				escape("++") +
				END, result);
	}
	
	@Test
	public void testConstant() {
		String code = "PI = 3.14159";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				constant("PI ") +
				escape("= ") +
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
		String expected = readFile("/src/test/resources/test_ruby_color.tex");
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testKeywordMethodCalledOnAString() {
		String code = "defined?(\"text\") # should return true";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				keyword("defined?") +
				escape("(") +
				string("\"text\"") +
				escape(") ") +
				comment("# should return true") +
				END, result);
	}
	
	@Test
	public void testHashAndArrayKeys() {
		String code = "@params[:id]";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				variable("@params") +
				escape("[") +
				symbol(":id") +
				escape("]") +
				END, result);
		code = "$instances[5]";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				variable("$instances") +
				escape("[") +
				number("5") +
				escape("]") +
				END, result);
		code = "dict['word']";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("dict[") +
				string("'word'") +
				escape("]") +
				END, result);
	}
	
	@Test
	public void testMultipleNamespaces() {
		String code = "class User < ActiveRecord::Base";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				keyword("class ") +
				constant("User ") +
				escape("< ") +
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
				number("1") + escape(",") +
				number("2") + escape(",") +
				number("3") + escape(",") +
				number("4") +
				escape("]") +
				END, result);
		code = "array = [1..4]";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				escape("array = [") +
				number("1") + escape("..") +
				number("4") +
				escape("]") +
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
		Assert.assertEquals(BEGIN + regex(code) + END, result);	}
	
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
				string("\"I'm crazy!\" ") +
				keyword("if ") +
				escape("(") +
				number("3") +
				escape("+") +
				number("4") +
				escape(")-") +
				number("2") +
				escape("+") +
				number("+10.8 ") +
				escape("= (") +
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
								escape("i+") +
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
		String code = "print <<EXP1, <<'puts', <<EXP2\n" +
				"#{params[:user].name}, write the code:\n" +
				"EXP1\n" +
				"puts \"#{params[:id]}\"\n" +
				"puts\n" +
				"to print the id\n" +
				"EXP2";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + 
				escape("print ") +
				string("<<EXP1, <<'puts', <<EXP2\n" +
						"#{}, write the code:\n" +
						"EXP1\n" +
						"puts \"#{params[:id]}\"\n" +
						"puts\n" +
						"to print the id\n" +
						"EXP2\n",
						escape("params[") +
						symbol(":user") +
						escape("].name")
				) +
		END, result);
	}
	
	@Test
	public void testCommentsAmongTextBug() {
		String code = "print a.name\n# a comment\nb = 10";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN +
				escape("print a.name\n") +
				comment("# a comment\n") +
				escape("b = ") + number("10") +
				END, result);
	}
	
	private String comment(String text) {
		return colorize("rubycomment", text);
	}
	
	private String string(String content, String... interpolations) {
		content = colorize("rubystring", content);
		for (String code : interpolations) {
			code = "\\#\\{" + code;
			if (!findLastMode(code).equals("string")) {
				code += "\\rubystring ";
			}
			code += "\\}";
			code = Matcher.quoteReplacement(code);
			content = content.replaceFirst("\\\\#\\\\\\{\\\\\\}", code);
		}
		return content;
	}
	
	private String findLastMode(String code) {
		Matcher matcher = Pattern.compile("\\\\ruby(.+?)\\s").matcher(code);
		String mode = "";
		while (matcher.find()) {
			mode = matcher.group(1);
		}
		return mode;
	}

	private String variable(String name) {
		return colorize("rubyvariable", name);
	}
	
	private String regex(String regex) {
		return colorize("rubyregexp", regex);
	}
	
	private String keyword(String word) {
		return colorize("rubykeyword", word);
	}
	
	private String number(String number) {
		return colorize("rubynumber", number);
	}
	
	private String constant(String name) {
		return colorize("rubyconstant", name);
	}
	
	private String symbol(String name) {
		return colorize("rubysymbol", name);
	}
	
	private String escape(String text) {
		return colorize("rubynormal", text);
	}
	
	private String colorize(String clazz, String content) {
		content = escapeBlah(content);
		content = escapeSpaces(content);
		content = escapeSymbols(content);
		return "\\" + clazz + " " + content;
	}
	
	private String escapeBlah(String string) {
		string = new EscapeTag().parse(string, null);
		string = Escape.HYPHEN.unescape(string);
		string = Escape.SHIFT_LEFT.unescape(string);
		string = Escape.SHIFT_RIGHT.unescape(string);
		return string;
	}
	
	private String escapeSymbols(String string) {
		return string.replaceAll("(-|<|>|=|\"|\'|/|@)", "\\\\verb#$1#");
	}
	
	private String escapeSpaces(String string) {
		string = string.replaceAll("\n", "\\\\\\\\\n");
		string = string.replaceAll("\t", "~~~~");
		return string.replaceAll(" ", "~");
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
}
