package br.com.caelum.tubaina.parser.html;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.ListTag;

public class ListTagTest {

	@Test
	public void testList() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "");
		Assert.assertEquals("<ul class=\"list\">conteudo da lista</ul>", result);
	}
	
	@Test
	public void testListNumber() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "number");
		Assert.assertEquals("<ol class=\"list\" type=\"1\">conteudo da lista</ol>", result);
	}
	
	@Test
	public void testListLetter() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "letter");
		Assert.assertEquals("<ol class=\"list\" type=\"a\">conteudo da lista</ol>", result);
	}
	
	@Test
	public void testListRoman() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "roman");
		Assert.assertEquals("<ol class=\"list\" type=\"I\">conteudo da lista</ol>", result);
	}
	
}
