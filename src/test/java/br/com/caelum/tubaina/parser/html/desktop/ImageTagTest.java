package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Test;

import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.ImageChunk;


public class ImageTagTest extends AbstractTagTest {

	@Test(expected=TubainaException.class)
	public void shouldThrowExceptionWhenTagContainsLabel() {
		ImageChunk chunk = new ImageChunk("image.png", "label=someLabel", 100, 1, new SectionsManager());
		getContent(chunk);
	}

}
