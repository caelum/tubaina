package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.kindle.ListTag;

public class ListTagTest {

	@Test
	public void testList() {
		ListTag tag = new ListTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("<ul>conteudo da lista</ul>", result);
	}
	
	@Test
	public void testListNumber() {
		ListTag tag = new ListTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("<ol>conteudo da lista</ol>", result);
	}
	
	@Test
	public void testListLetter() {
		ListTag tag = new ListTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("<ol class=\"letter\">conteudo da lista</ol>", result);
	}
	
	@Test
	public void testListRoman() {
		ListTag tag = new ListTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("<ol class=\"roman\">conteudo da lista</ol>", result);
	}
	
}
