package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

public class BoxTagTest {

	@Test
	public void testBox() {
		BoxTag tag = new BoxTag();
		String result = tag.parse("Texto do Box", "Titulo do Box");
		Assert.assertEquals("\\boxtag{Titulo do Box}{Texto do Box}", result);
	}
	
	@Test
	public void testBoxWithMultilineContent() {
		BoxTag tag = new BoxTag();
		String result = tag.parse("Texto do Box\n blablabla\n", "Titulo do Box");
		Assert.assertEquals("\\boxtag{Titulo do Box}{Texto do Box\n blablabla\n}", result);
	}
}
