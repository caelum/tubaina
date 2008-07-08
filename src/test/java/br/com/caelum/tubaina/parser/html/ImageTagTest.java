package br.com.caelum.tubaina.parser.html;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.Tag;

public class ImageTagTest {

	@Test
	public void testFullImageTag() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "w=30 \"Imagem de alguma coisa\"");
		Assert.assertEquals(
				"<div class=\"image\"><img src=\"../resources/imagem.png\" alt=\"Imagem de alguma coisa\" />"
						+ "<span class=\"image\">Imagem de alguma coisa</span></div>", result);
	}

	@Test
	public void testImageTagWithoutBounds() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "\"Imagem de alguma coisa\"");
		Assert.assertEquals("<div class=\"image\"><img src=\"../resources/imagem.png\" alt=\"Imagem de alguma coisa\" />"
				+ "<span class=\"image\">Imagem de alguma coisa</span></div>", result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "w=42");
		Assert.assertEquals(
				"<div class=\"image\"><img src=\"../resources/imagem.png\" alt=\"imagem.png\" /></div>",
				result);
	}
	
	@Test
	public void testImageTagWithPath() {
		Tag tag = new ImageTag();
		String result = tag.parse("some/path/imagem.png", "w=42");
		Assert.assertEquals(
				"<div class=\"image\"><img src=\"../resources/imagem.png\" alt=\"imagem.png\" /></div>",
				result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		Tag tag = new ImageTag();
		String result = tag.parse("some/path/imagem.png", "w=50%");
		Assert.assertEquals(
				"<div class=\"image\"><img src=\"../resources/imagem.png\" alt=\"imagem.png\" /></div>",
				result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		Tag tag = new ImageTag();
		String result = tag.parse("some/path/imagem.png", "w=50");
		Assert.assertEquals(
				"<div class=\"image\"><img src=\"../resources/imagem.png\" alt=\"imagem.png\" /></div>",
				result);
	}
	
}
