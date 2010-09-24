package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

public class BoxTagTest {

	@Test
	public void testBox() {
		BoxTag tag = new BoxTag();
		String result = tag.parse("Texto do Box", "Titulo do Box");
		Assert.assertEquals("\\begin{tubainabox}{Titulo do Box}\nTexto do Box\n\\end{tubainabox}", result);
	}
	
	@Test
	public void testBoxWithMultilineContent() {
		BoxTag tag = new BoxTag();
		String result = tag.parse("Texto do Box\n blablabla\n", "Titulo do Box");
		Assert.assertEquals("\\begin{tubainabox}{Titulo do Box}\nTexto do Box\n blablabla\n\n\\end{tubainabox}", result);
	}
	
	@Test
	public void testChangeTitleToEmptyStringIfItsNull(){
		String result = new BoxTag().parse("Text", null);
		Assert.assertEquals("\\begin{tubainabox}{}\nText\n\\end{tubainabox}", result);
	}

}
