package br.com.caelum.tubaina.parser.latex;


import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.BoxChunk;

public class BoxTagTest extends AbstractTagTest {

	@Test
	public void testBox() {
		String result = getContent(new BoxChunk("Titulo do Box", text("Texto do Box")));
		Assert.assertEquals("\\begin{tubainabox}{Titulo do Box}\nTexto do Box\n\\end{tubainabox}", result);
	}
	
	@Test
	public void testBoxWithMultilineContent() {
		String result = getContent(new BoxChunk("Titulo do Box", text("Texto do Box\n blablabla\n")));
		Assert.assertEquals("\\begin{tubainabox}{Titulo do Box}\nTexto do Box\n blablabla\n\n\\end{tubainabox}", result);
	}
	
	@Test
	public void testChangeTitleToEmptyStringIfItsNull(){
		String result = getContent(new BoxChunk(null, text("Text")));
		Assert.assertEquals("\\begin{tubainabox}{}\nText\n\\end{tubainabox}", result);
	}

}
