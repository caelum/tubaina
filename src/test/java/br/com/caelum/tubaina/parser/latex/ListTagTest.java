package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

public class ListTagTest {

	@Test
	public void testList() {
		ListTag tag = new ListTag();
		String result = tag.parse("\nconteudo da lista", "");
		Assert.assertEquals("\n\n\\begin{itemize}\nconteudo da lista\\end{itemize}", result);
	}
	
	@Test
	public void testListNumber() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "number");
		Assert.assertEquals("\n\n\\begin{enumerate}[1)]\nconteudo da lista\n\\end{enumerate}", result);
	}
	
	@Test
	public void testListLetter() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "letter");
		Assert.assertEquals("\n\n\\begin{enumerate}[a)]\nconteudo da lista\n\\end{enumerate}", result);
	}
	
	@Test
	public void testListRoman() {
		ListTag tag = new ListTag();
		String result = tag.parse("conteudo da lista", "roman");
		Assert.assertEquals("\n\n\\begin{enumerate}[I)]\nconteudo da lista\n\\end{enumerate}", result);
	}
	

}
