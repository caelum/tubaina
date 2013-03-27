package br.com.caelum.tubaina.parser.html.desktop;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.RegexTag;

public class HtmlParserTest {

	private HtmlParser parser;
	
	@Before
	public void setUp() throws IOException {
		RegexConfigurator configurator = new RegexConfigurator();
		List<RegexTag> tags = configurator.read("/regex.properties", "/html.properties");
		this.parser = new HtmlParser(tags);
	}

	@Test
	public void testBoldTagInline() {
		String result = parser.parse("ola **mundo**");
		Assert.assertEquals("ola <strong>mundo</strong>", result);
	}

	@Test
	public void testBoldTagMultiline() {
		String result = parser.parse("ola **mu\nndo**");
		Assert.assertEquals("ola <strong>mu\nndo</strong>", result);
	}

	@Test
	public void testItalicTagInline() {
		String result = parser.parse("ola ::mundo::");
		Assert.assertEquals("ola <em>mundo</em>", result);
	}

	@Test
	public void testItalicTagMultiline() {
		String result = parser.parse("ola ::mu\nndo::");
		Assert.assertEquals("ola <em>mu\nndo</em>", result);
	}

	@Test
	public void testUnderlineTagInline() {
		String result = parser.parse("ola __mundo__");
		Assert.assertEquals("ola <u>mundo</u>", result);
	}

	@Test
	public void testUnderlineTagMultiline() {
		String result = parser.parse("ola __mu\nndo__");
		Assert.assertEquals("ola <u>mu\nndo</u>", result);
	}

	@Test
	public void testInlineCodeTagInline() {
		String result = parser.parse("ola %%mundo%%");
		Assert.assertEquals("ola <code>mundo</code>", result);
	}

	@Test
	public void testInlineCodeTagMultiline() {
		String result = parser.parse("ola %%mu\nndo%%");
		Assert.assertEquals("ola <code>mu\nndo</code>", result);
	}
	
	// Test for inline Ruby code with inheritance symbol (::)
	@Test
	public void testTwoInlineCodeTagsWithTwoColonsInside() {
		String result = parser.parse("ola %%mundo <:: superclasse%% texto %%mais codigo <:: superclasse%%");
		Assert.assertEquals("ola <code>mundo &#58;&#58; superclasse</code> texto <code>mais codigo &#58;&#58; superclasse</code>", result);
	}

	@Test
	public void testQuotationTagInline() {
		String result = parser.parse("[quote ola mundo --Anonimo]");
		Assert.assertEquals("<q cite=\"Anonimo\">ola mundo</q>", result);
	}
	
	@Test
	public void testQuotationTagMultiline() {
		String result = parser.parse("[quote ola mu\nndo-- Anonimo]");
		Assert.assertEquals("<q cite=\"Anonimo\">ola mu\nndo</q>", result);
	}

	@Test
	public void testLabelTagInline() {
		String result = parser.parse("[footnote ola mundo]HelloWorld[/footnote]");
		Assert.assertEquals("<label title=\"ola mundo\">HelloWorld<span>?</span></label>", result);
	}
	
	@Test
	public void testLabelTagMultiline() {
		String result = parser.parse("[footnote ola \nmundo]Hello\nWorld[/footnote]");
		Assert.assertEquals("<label title=\"ola \nmundo\">Hello\nWorld<span>?</span></label>", result);
	}
	
	@Test
	public void testLinkWithHttpTagInline() {
		String result = parser.parse("http://www.caelum.com.br");
		Assert.assertEquals("<a href=\"http://www.caelum.com.br\">http://www.caelum.com.br</a>", result);
	}
	
	@Test
	public void testLinkWithHttpsTagInline() {
		String result = parser.parse("https://www.caelum.com.br");
		Assert.assertEquals("<a href=\"https://www.caelum.com.br\">https://www.caelum.com.br</a>", result);
	}
	
	@Test
	public void testLinkWithParenthesis() {
		String result = parser.parse("(http://www.caelum.com.br)");
		Assert.assertEquals("(<a href=\"http://www.caelum.com.br\">http://www.caelum.com.br</a>)", result);
	}
	
	@Test
	public void testMailTagInline() {
		String result = parser.parse("[mail]olamundo@caelum.com.br[/mail]");
		Assert.assertEquals("olamundo@caelum.com.br", result);
	}
	
	@Test
	public void testMailTagWithUnderscoreInline() {
		String result = parser.parse("[mail]ola\\_mundo@caelum.com.br[/mail]");
		Assert.assertEquals("ola\\_mundo@caelum.com.br", result);
	}
	
	@Test
	public void testTitleTagInline() {
		String result = parser.parse("[title ola mundo]");
		Assert.assertEquals("<h3>ola mundo</h3>", result);
	}
	
	@Test
	public void testTitleTagInlineWithInnerTags() {
		String result = parser.parse("[title ola **mu__n__do**]");
		Assert.assertEquals("<h3>ola <strong>mu<u>n</u>do</strong></h3>", result);
	}
	
	@Test
	public void testQuotationTag(){
		String result = parser.parse("\"\"");
		Assert.assertEquals("\"\"", result);
	}
	
	@Test
	public void testQuotationTagWithText(){
		String result = parser.parse("\"qualquer coisa escrito aqui\"");
		Assert.assertEquals("\"qualquer coisa escrito aqui\"", result);
	}
	
}
