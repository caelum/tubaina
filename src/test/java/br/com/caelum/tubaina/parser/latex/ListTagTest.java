package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.ListChunk;

public class ListTagTest extends AbstractTagTest {

	@Test
	public void testList() {
		ListChunk chunk = new ListChunk("", text("conteudo da lista"));
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{itemize}\nconteudo da lista\\end{itemize}", result);
	}
	
	@Test
	public void testListNumber() {
		ListChunk chunk = new ListChunk("number", text("conteudo da lista"));
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{enumerate}[1)]\nconteudo da lista\n\\end{enumerate}", result);
	}
	
	@Test
	public void testListLetter() {
		ListChunk chunk = new ListChunk("letter", text("conteudo da lista"));
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{enumerate}[a)]\nconteudo da lista\n\\end{enumerate}", result);
	}
	
	@Test
	public void testListRoman() {
		ListChunk chunk = new ListChunk("roman", text("conteudo da lista"));
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{enumerate}[I)]\nconteudo da lista\n\\end{enumerate}", result);
	}
	

}
