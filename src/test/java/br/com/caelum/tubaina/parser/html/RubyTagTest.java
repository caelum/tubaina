package br.com.caelum.tubaina.parser.html;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;

public class RubyTagTest {
	private final String BEGIN = "<div class=\"ruby\"><code class=\"ruby\">";
	private final String END = "</code></div>";
	private final RubyTag rubyTag = new RubyTag(new SimpleIndentator());

	@Test
	public void testComments() {
		String code = "# this is a ruby comment\n";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubycomment\">#&nbsp;this&nbsp;is&nbsp;a&nbsp;ruby&nbsp;comment</span>"
				+ END, result);
		code = "=begin\nThis is a\nmultiline comment\n=end";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubycomment\">=begin<br/>\nThis&nbsp;is&nbsp;a<br/>\nmultiline&nbsp;comment<br/>\n=end</span>"
				+ END, result);
	}

	@Test
	public void testSingleQuotedString() {
		String code = "\"this is a double quoted string\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubystring\">\"this&nbsp;is&nbsp;a&nbsp;double&nbsp;quoted&nbsp;string\"</span>"
				+ END, result);
	}

	@Test
	public void testDoubleQuotedString() {
		String code;
		String result;
		code = "'this is a single quoted string'";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubystring\">'this&nbsp;is&nbsp;a&nbsp;single&nbsp;quoted&nbsp;string'</span>"
				+ END, result);
	}

	@Test
	public void testGeneralizedSingleQuotedString() {
		String code;
		String result;
		code = "%q!I said, \"You said, 'She said it.'\"!";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubystring\">%q!I&nbsp;said,&nbsp;\"You&nbsp;said,&nbsp;'She&nbsp;said&nbsp;it.'\"!</span>"
				+ END, result);
	}

	@Test
	public void testGeneralizedDoubleQuotedString() {
		String code;
		String result;
		code = "%Q/I said, \"You said, 'She said it.'\"/";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubystring\">%Q/I&nbsp;said,&nbsp;\"You&nbsp;said,&nbsp;'She&nbsp;said&nbsp;it.'\"/</span>"
				+ END, result);
		code = "%(I said, \"You said, 'She said it.'\")";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubystring\">%(I&nbsp;said,&nbsp;\"You&nbsp;said,&nbsp;'She&nbsp;said&nbsp;it.'\")</span>"
				+ END, result);
	}

	@Test
	public void testQuotedTextInsideCommentIsNotString() {
		String code = "# this is a comment with 'quoted' and \"double quoted\" text";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubycomment\">#&nbsp;this&nbsp;is&nbsp;a&nbsp;comment&nbsp;with&nbsp;'quoted'&nbsp;and&nbsp;\"double&nbsp;quoted\"&nbsp;text</span>"
				+ END, result);
	}

	@Test
	public void testMultipleStringsInSameLine() {
		String code = "method 'a string', \"another string\", %q/yet another string/";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "method&nbsp;<span class=\"rubystring\">'a&nbsp;string'</span>,&nbsp;<span class=\"rubystring\">\"another&nbsp;string\"</span>,&nbsp;<span class=\"rubystring\">%q/yet&nbsp;another&nbsp;string/</span>"
				+ END, result);
	}

	@Test
	public void testDoubleQuotedTextInsideSingleQuotedString() {
		String code = "'this is a \"single-quoted\" string'";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubystring\">'this&nbsp;is&nbsp;a&nbsp;\"single-quoted\"&nbsp;string'</span>"
				+ END, result);
	}

	@Test
	public void testLineOrientedStringLiteral() {
		String code = "puts <<BLAH\nThis is a\nmultiline, line-oriented\nstring\nBLAH";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "puts&nbsp;<span class=\"rubystring\">&lt;&lt;BLAH<br/>\nThis&nbsp;is&nbsp;a<br/>\nmultiline,&nbsp;line-oriented<br/>\nstring<br/>\nBLAH</span>"
				+ END, result);
		code = "<<`CODE`\necho \"hello\"\necho \"world\"\nCODE";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubystring\">&lt;&lt;`CODE`<br/>\necho&nbsp;\"hello\"<br/>\necho&nbsp;\"world\"<br/>\nCODE</span>"
				+ END, result);
	}

	@Test
	public void testSingleQuotedTextInsideDoubleQuotedString() {
		String code = "puts \"He said: 'Hello World!'\"";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "puts&nbsp;<span class=\"rubystring\">\"He&nbsp;said:&nbsp;'Hello&nbsp;World!'\"</span>"
				+ END, result);
	}

	@Test
	public void testReservedWords() {
		String code = "BEGIN class ensure nil self when END def false not super while alias defined for or then yield and do if redo true begin else in rescue undef break elsif module retry unless case end next return until raise defined?";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN
				+ "<span class=\"rubykeyword\">BEGIN</span>&nbsp;<span class=\"rubykeyword\">class</span>&nbsp;"
				+ "<span class=\"rubykeyword\">ensure</span>&nbsp;<span class=\"rubykeyword\">nil</span>&nbsp;"
				+ "<span class=\"rubykeyword\">self</span>&nbsp;<span class=\"rubykeyword\">when</span>&nbsp;"
				+ "<span class=\"rubykeyword\">END</span>&nbsp;<span class=\"rubykeyword\">def</span>&nbsp;"
				+ "<span class=\"rubykeyword\">false</span>&nbsp;<span class=\"rubykeyword\">not</span>&nbsp;"
				+ "<span class=\"rubykeyword\">super</span>&nbsp;<span class=\"rubykeyword\">while</span>&nbsp;"
				+ "<span class=\"rubykeyword\">alias</span>&nbsp;<span class=\"rubykeyword\">defined</span>&nbsp;"
				+ "<span class=\"rubykeyword\">for</span>&nbsp;<span class=\"rubykeyword\">or</span>&nbsp;"
				+ "<span class=\"rubykeyword\">then</span>&nbsp;<span class=\"rubykeyword\">yield</span>&nbsp;"
				+ "<span class=\"rubykeyword\">and</span>&nbsp;<span class=\"rubykeyword\">do</span>&nbsp;"
				+ "<span class=\"rubykeyword\">if</span>&nbsp;<span class=\"rubykeyword\">redo</span>&nbsp;"
				+ "<span class=\"rubykeyword\">true</span>&nbsp;<span class=\"rubykeyword\">begin</span>&nbsp;"
				+ "<span class=\"rubykeyword\">else</span>&nbsp;<span class=\"rubykeyword\">in</span>&nbsp;"
				+ "<span class=\"rubykeyword\">rescue</span>&nbsp;<span class=\"rubykeyword\">undef</span>&nbsp;"
				+ "<span class=\"rubykeyword\">break</span>&nbsp;<span class=\"rubykeyword\">elsif</span>&nbsp;"
				+ "<span class=\"rubykeyword\">module</span>&nbsp;<span class=\"rubykeyword\">retry</span>&nbsp;"
				+ "<span class=\"rubykeyword\">unless</span>&nbsp;<span class=\"rubykeyword\">case</span>&nbsp;"
				+ "<span class=\"rubykeyword\">end</span>&nbsp;<span class=\"rubykeyword\">next</span>&nbsp;"
				+ "<span class=\"rubykeyword\">return</span>&nbsp;<span class=\"rubykeyword\">until</span>&nbsp;"
				+ "<span class=\"rubykeyword\">raise</span>&nbsp;<span class=\"rubykeyword\">defined?</span>"
				+ END, result);
	}
	
	@Test
	public void testRegularExpressions() {
		String code = "/regexp/";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubyregexp\">/regexp/</span>" + END, result);
		code = "%r(another/regexp/)";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubyregexp\">%r(another/regexp/)</span>" + END, result);
		code = "/ReGeXp/i";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubyregexp\">/ReGeXp/i</span>" + END, result);
	}
	
	@Test
	public void testVariables() {
		String code = "@local = nil";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubyvariable\">@local</span>&nbsp;=&nbsp;<span class=\"rubykeyword\">nil</span>" + END, result);
		code = "@@count = 1";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubyvariable\">@@count</span>&nbsp;=&nbsp;<span class=\"rubynumber\">1</span>" + END, result);
		code = "$counter++";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubyvariable\">$counter</span>++" + END, result);
	}
	
	@Test
	public void testConstant() {
		String code = "PI = 3.14159";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubyconstant\">PI</span>&nbsp;=&nbsp;<span class=\"rubynumber\">3.14159</span>" + END, result);
	}
	
	@Test
	public void testNumbers() {
		String code = "12345";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubynumber\">12345</span>" + END, result);
		code = "123.45";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubynumber\">123.45</span>" + END, result);
		code = "123e45";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubynumber\">123e45</span>" + END, result);
		code = "123E-45";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubynumber\">123E-45</span>" + END, result);
		code = "-123.45e67";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubynumber\">-123.45e67</span>" + END, result);
		code = "0xffffff";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubynumber\">0xffffff</span>" + END, result);
		code = "0b010010";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubynumber\">0b010010</span>" + END, result);
		code = "01777";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubynumber\">01777</span>" + END, result);
	}
	
	@Test
	public void testSymbols() {
		String code = "attr_accessor :name, :age";
		String result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "attr_accessor&nbsp;<span class=\"rubysymbol\">:name</span>,&nbsp;<span class=\"rubysymbol\">:age</span>" + END, result);
		code = "%s(strange_symbol)";
		result = rubyTag.parse(code, "");
		Assert.assertEquals(BEGIN + "<span class=\"rubysymbol\">%s(strange_symbol)</span>" + END, result);
	}
}
