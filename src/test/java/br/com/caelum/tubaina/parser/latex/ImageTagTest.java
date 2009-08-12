package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.Tag;

public class ImageTagTest {

	@Test
	public void testFullImageTag() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "w=30 \"Imagem de alguma coisa\"");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=52.5mm]{imagem.png}\n" +
				"\n\n\\caption{Imagem de alguma coisa}\n\n" +
				"\\end{figure}\n\n", result);
	}

	@Test
	public void testImageTagWithoutBounds() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "\"Imagem de alguma coisa\"");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=\\textwidth]{imagem.png}\n" +
				"\n\n\\caption{Imagem de alguma coisa}\n\n" +
				"\\end{figure}\n\n", result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "w=42");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "w=40%");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=70.0mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "w=40");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=70.0mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithInvalidBounds() {
		Tag tag = new ImageTag();
		String result = tag.parse("imagem.png", "w=42");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithPath() {
		Tag tag = new ImageTag();
		String result = tag.parse("some/path/imagem.png", "w=42");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
}
