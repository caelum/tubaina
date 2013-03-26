package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.BoxChunk;

public class BoxTagTest extends AbstractTagTest {
	
	@Test
	public void testBox() {
		BoxChunk chunk = new BoxChunk("Titulo do Box", text("Texto do Box"));
		String result = getContent(chunk);
		Assert.assertEquals("<div class=\"box\"><h4>Titulo do Box</h4>\nTexto do Box</div>", result);
	}
	
	@Test
	public void testBoxWithMultilineContent() {
		BoxChunk chunk = new BoxChunk("Titulo do Box", text("Texto do Box\n blablabla"));
		String result = getContent(chunk);		
		Assert.assertEquals("<div class=\"box\"><h4>Titulo do Box</h4>\nTexto do Box\n blablabla</div>", result);
	}
	
}
