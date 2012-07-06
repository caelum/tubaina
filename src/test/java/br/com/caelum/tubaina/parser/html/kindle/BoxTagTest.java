package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

public class BoxTagTest {
	
	@Test
	public void testBox() {
		BoxTag tag = new BoxTag();
		String result = tag.parse("Texto do Box", "Titulo do Box");
		Assert.assertEquals("<hr/><b>Titulo do Box</b>\nTexto do Box<hr/>", result);
	}
	
	@Test
	public void testBoxWithMultilineContent() {
		BoxTag tag = new BoxTag();
		String result = tag.parse("Texto do Box\n blablabla\n", "Titulo do Box");
		Assert.assertEquals("<hr/><b>Titulo do Box</b>\nTexto do Box\n blablabla<hr/>", result);
	}
	
}
