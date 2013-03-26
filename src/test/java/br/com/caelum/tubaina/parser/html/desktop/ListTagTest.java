package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.ListChunk;

public class ListTagTest extends AbstractTagTest {

	@Test
	public void testList() {
		ListChunk chunk = new ListChunk("", text("conteudo da lista"));
		String result = getContent(chunk);
		Assert.assertEquals("<ul>conteudo da lista</ul>", result);
	}
	
	@Test
	public void testListNumber() {
		ListChunk chunk = new ListChunk("number", text("conteudo da lista"));
		String result = getContent(chunk);
		Assert.assertEquals("<ol>conteudo da lista</ol>", result);
	}
	
	@Test
	public void testListLetter() {
		ListChunk chunk = new ListChunk("letter", text("conteudo da lista"));
		String result = getContent(chunk);
		Assert.assertEquals("<ol class=\"letter\">conteudo da lista</ol>", result);
	}
	
	@Test
	public void testListRoman() {
		ListChunk chunk = new ListChunk("roman", text("conteudo da lista"));
		String result = getContent(chunk);
		Assert.assertEquals("<ol class=\"roman\">conteudo da lista</ol>", result);
	}
	
}
