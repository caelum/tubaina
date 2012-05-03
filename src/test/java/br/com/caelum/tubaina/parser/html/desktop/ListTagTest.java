package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.desktop.ListTag;

public class ListTagTest {

	@Test
	public void testList() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "");
		Assert.assertEquals("<ul>conteudo da lista</ul>", result);
	}
	
	@Test
	public void testListNumber() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "number");
		Assert.assertEquals("<ol>conteudo da lista</ol>", result);
	}
	
	@Test
	public void testListLetter() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "letter");
		Assert.assertEquals("<ol class=\"letter\">conteudo da lista</ol>", result);
	}
	
	@Test
	public void testListRoman() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "roman");
		Assert.assertEquals("<ol class=\"roman\">conteudo da lista</ol>", result);
	}
	
}
