package br.com.caelum.tubaina.parser.html;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.Tag;

public class HtmlParserTest {

	private HtmlParser parser;

	@Before
	public void setUp() throws IOException {
		RegexConfigurator configurator = new RegexConfigurator();
		List<Tag> tags = configurator.read("/regex.properties", "/html.properties");
		this.parser = new HtmlParser(tags, false);
	}

	@Test
	public void testBoldTagInline() {
		String result = parser.parse("ola **mundo**");
		Assert.assertEquals("ola <strong class=\"definition\">mundo</strong>", result);
	}

	@Test
	public void testBoldTagMultiline() {
		String result = parser.parse("ola **mu\nndo**");
		Assert.assertEquals("ola <strong class=\"definition\">mu\nndo</strong>", result);
	}

	@Test
	public void testItalicTagInline() {
		String result = parser.parse("ola ::mundo::");
		Assert.assertEquals("ola <em class=\"italic\">mundo</em>", result);
	}

	@Test
	public void testItalicTagMultiline() {
		String result = parser.parse("ola ::mu\nndo::");
		Assert.assertEquals("ola <em class=\"italic\">mu\nndo</em>", result);
	}

	@Test
	public void testUnderlineTagInline() {
		String result = parser.parse("ola __mundo__");
		Assert.assertEquals("ola <em class=\"underlined\">mundo</em>", result);
	}

	@Test
	public void testUnderlineTagMultiline() {
		String result = parser.parse("ola __mu\nndo__");
		Assert.assertEquals("ola <em class=\"underlined\">mu\nndo</em>", result);
	}

	@Test
	public void testInlineCodeTagInline() {
		String result = parser.parse("ola %%mundo%%");
		Assert.assertEquals("ola <code class=\"inlineCode\">mundo</code>", result);
	}

	@Test
	public void testInlineCodeTagMultiline() {
		String result = parser.parse("ola %%mu\nndo%%");
		Assert.assertEquals("ola <code class=\"inlineCode\">mu\nndo</code>", result);
	}
	
	// Test for inline Ruby code with inheritance symbol (::)
	@Test
	public void testTwoInlineCodeTagsWithTwoColonsInside() {
		String result = parser.parse("ola %%mundo <:: superclasse%% texto %%mais codigo <:: superclasse%%");
		Assert.assertEquals("ola <code class=\"inlineCode\">mundo &#58;&#58; superclasse</code> texto <code class=\"inlineCode\">mais codigo &#58;&#58; superclasse</code>", result);
	}
	
	@Test
	public void testParagraphTagWithInnerTagsInline() {
		String result = parser.parseParagraph("**Ola** ::mundo::. %%Tchau%% **::__mundo__::**.");
		Assert.assertEquals("<span class=\"paragraph\"><strong class=\"definition\">Ola</strong> <em class=\"italic\">mundo</em>. <code class=\"inlineCode\">Tchau</code> <strong class=\"definition\"><em class=\"italic\"><em class=\"underlined\">mundo</em></em></strong>.</span>", result);
	}

	@Test
	public void testQuotationTagInline() {
		String result = parser.parse("[quote ola mundo --Anonimo]");
		Assert.assertEquals("<span class=\"quoteContent\">ola mundo</span><span class=\"quoteAuthor\">Anonimo</span>", result);
	}
	
	@Test
	public void testQuotationTagMultiline() {
		String result = parser.parse("[quote ola mu\nndo-- Anonimo]");
		Assert.assertEquals("<span class=\"quoteContent\">ola mu\nndo</span><span class=\"quoteAuthor\">Anonimo</span>", result);
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
	public void testLinkComHttpTagInline() {
		String result = parser.parse("http://www.caelum.com.br");
		Assert.assertEquals("<a class=\"link\" target=\"_blank\" href=\"http://www.caelum.com.br\">http://www.caelum.com.br</a>", result);
	}
	
	@Test
	public void testLinkComParentesis() {
		String result = parser.parse("(http://www.caelum.com.br)");
		Assert.assertEquals("(<a class=\"link\" target=\"_blank\" href=\"http://www.caelum.com.br\">http://www.caelum.com.br</a>)", result);
	}
	
//	@Test
//	public void testLinkSemHttpTagInline() {
//		String result = parser.parse("[url ola mundo | www.caelum.com.br]");
//		Assert.assertEquals("<a class=\"link\" target=\"_blank\" href=\"www.caelum.com.br\">ola mundo</a>", result);
//	}
//
//	@Test
//	public void testLinkTagMultiline() {
//		String result = parser.parse("[url ola mu\nndo | http://www.caelum.com.br]");
//		Assert.assertEquals("<a class=\"link\" target=\"_blank\" href=\"http://www.caelum.com.br\">ola mu\nndo</a>", result);
//	}

	@Test
	public void testMailTagInline() {
		String result = parser.parse("[mail]olamundo@caelum.com.br[/mail]");
		Assert.assertEquals("<a class=\"email\" href=\"mailto:olamundo@caelum.com.br\">olamundo em caelum.com.br</a>", result);
	}
	
	@Test
	public void testMailTagWithUnderscoreInline() {
		String result = parser.parse("[mail]ola\\_mundo@caelum.com.br[/mail]");
		Assert.assertEquals("<a class=\"email\" href=\"mailto:ola\\_mundo@caelum.com.br\">ola\\_mundo em caelum.com.br</a>", result);
	}
	
	@Test
	public void testTitleTagInline() {
		String result = parser.parse("[title ola mundo]");
		Assert.assertEquals("<strong class=\"title\">ola mundo</strong>", result);
	}
	
	@Test
	public void testTitleTagInlineWithInnerTags() {
		String result = parser.parse("[title ola **mu__n__do**]");
		Assert.assertEquals("<strong class=\"title\">ola <strong class=\"definition\">mu<em class=\"underlined\">n</em>do</strong></strong>", result);
	}
	
	@Test
	public void testBoxTagWithoutInnerTags() {
		String result = parser.parseBox("ola mundo", "Titulo do Box");
		Assert.assertEquals("<div class=\"box\"><h3>Titulo do Box</h3>\nola mundo</div>", result);
	}
	
	@Test
	public void testBoxTagWithInnerTags() {
		//Should not parse. BoxTag just create the box structure
		String result = parser.parseBox("__ola__ **mundo**", "Titulo do Box");
		Assert.assertEquals("<div class=\"box\"><h3>Titulo do Box</h3>\n__ola__ **mundo**</div>", result);
	}
	
	@Test
	public void testBoxTagWithInnerTagsOnTitle() {
		//Should not parse. BoxTag just create the box structure
		String result = parser.parseBox("ola mundo", "Titulo **do Box**");
		Assert.assertEquals("<div class=\"box\"><h3>Titulo **do Box**</h3>\nola mundo</div>", result);
	}
	
//	@Test
//	public void testJavaTag() {
//		String string = "\nvoid olaMundo{\n    System.out.println(\"ola mundo\");\n})";
//		String result = parser.parseJava(string);
//		String j2hString = Java2Html.convertToHtml(string.substring(1));
//		j2hString = j2hString.replaceAll("<!--(.+?)-->\n", "").trim();
//		Assert.assertEquals(j2hString, result);
//	}
	
	@Test
	public void testBulletedList() {
		String result = parser.parseList("conteudo da lista", "algo que nao importa");
		Assert.assertEquals("<ul class=\"list\">conteudo da lista</ul>", result);
	}

	@Test
	public void testNumberList() {
		String result = parser.parseList("conteudo da lista", "number");
		Assert.assertEquals("<ol class=\"list\" type=\"1\">conteudo da lista</ol>", result);
	}
	
	@Test
	public void testLetterList() {
		String result = parser.parseList("conteudo da lista", "letter");
		Assert.assertEquals("<ol class=\"list\" type=\"a\">conteudo da lista</ol>", result);
	}
	
	@Test
	public void testRomanList() {
		String result = parser.parseList("conteudo da lista", "roman");
		Assert.assertEquals("<ol class=\"list\" type=\"I\">conteudo da lista</ol>", result);
	}
	
	@Test
	public void testTagSoloTag() {
		String result = parser.parseIndex("ola mundo");
		Assert.assertEquals("\n<a id=\"ola mundo\"></a>\n", result);
	}
	
	@Test
	public void testTagMultiTag() {
		String result = parser.parseIndex("ola mundo, olamundo");
		Assert.assertEquals("\n<a id=\"ola mundo, olamundo\"></a>\n", result);
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
	
	@Test
	public void testItemSplittBug(){
		String input = "* Refactoring, Martin Fowler\n\n" +
				"* Effective Java, Joshua Bloch\n\n* Design Patterns, Erich Gamma et al";
		List<Chunk> chunks = new ChunkSplitter(null, "list").splitChunks(input);
		Assert.assertEquals(3, chunks.size());
		Assert.assertEquals("<li><span class=\"paragraph\">Refactoring, Martin Fowler</span></li>", chunks.get(0).getContent(parser));
		Assert.assertEquals("<li><span class=\"paragraph\">Effective Java, Joshua Bloch</span></li>", chunks.get(1).getContent(parser));
		Assert.assertEquals("<li><span class=\"paragraph\">Design Patterns, Erich Gamma et al</span></li>", chunks.get(2).getContent(parser));
	}
	
}


