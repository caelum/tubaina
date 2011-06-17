package br.com.caelum.tubaina.parser.html;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ImageTagTest {

	private ImageTag tag;

	@Before
	public void setUp() {
		tag = new ImageTag();
	}

	@Test
	public void testFullImageTag() {
		String result = tag.parse("imagem.png", "w=30 \"Imagem de alguma coisa\"");
		Assert.assertEquals(
				"<img src=\"$$RELATIVE$$/imagem.png\" alt=\"Imagem de alguma coisa\" />", result);
	}

	@Test
	public void testImageTagWithoutBounds() {
		String result = tag.parse("imagem.png", "\"Imagem de alguma coisa\"");
		Assert.assertEquals(
				"<img src=\"$$RELATIVE$$/imagem.png\" alt=\"Imagem de alguma coisa\" />", result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		String result = tag.parse("imagem.png", "w=42");
		Assert.assertEquals(
				"<img src=\"$$RELATIVE$$/imagem.png\" alt=\"imagem.png\" />", result);
	}
	
	@Test
	public void testImageTagWithPath() {
		String result = tag.parse("some/path/imagem.png", "w=42");
		Assert.assertEquals(
				"<img src=\"$$RELATIVE$$/imagem.png\" alt=\"imagem.png\" />", result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		String result = tag.parse("some/path/imagem.png", "w=50%");
		Assert.assertEquals(
				"<img src=\"$$RELATIVE$$/imagem.png\" alt=\"imagem.png\" />", result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		String result = tag.parse("some/path/imagem.png", "w=50");
		Assert.assertEquals(
				"<img src=\"$$RELATIVE$$/imagem.png\" alt=\"imagem.png\" />", result);
	}
	
}
