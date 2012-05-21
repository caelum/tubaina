package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.parser.Tag;

public class ImageTagTest {

	private Tag tag;

	@Before
	public void setUp() {
		tag = new ImageTag();
	}
	
	@Test
	public void testFullImageTag() {
		String result = tag.parse("imagem.png", "w=30 \"Imagem de alguma coisa\"");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=52.5mm]{imagem.png}\n" +
				"\n\n\\caption{Imagem de alguma coisa}\n\n" +
				"\\end{figure}\n\n", result);
	}

	@Test
	public void labelAndNoCaption() throws Exception {
		String result = tag.parse("image.png", "label=important");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\\label{important}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void labelNotInformed() throws Exception {
		String result = tag.parse("image.png", "label=");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\\label{image.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void labelNotInformedFollowedByACaption() throws Exception {
		String result = tag.parse("image.png", "label= \"a caption to the image\"");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\n\n\\caption{a caption to the image}\n\n" +
				"\\label{image.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void labelAndCaption() throws Exception {
		String result = tag.parse("image.png", "label=important \"a caption to the image\"");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\n\n\\caption{a caption to the image}\n\n" +
				"\\label{important}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithoutBounds() {
		String result = tag.parse("imagem.png", "\"Imagem de alguma coisa\"");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=\\textwidth]{imagem.png}\n" +
				"\n\n\\caption{Imagem de alguma coisa}\n\n" +
				"\\end{figure}\n\n", result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		String result = tag.parse("imagem.png", "w=42");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		String result = tag.parse("imagem.png", "w=40%");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=70.0mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		String result = tag.parse("imagem.png", "w=40");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=70.0mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithInvalidBounds() {
		String result = tag.parse("imagem.png", "w=42");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void testImageTagWithPath() {
		String result = tag.parse("some/path/imagem.png", "w=42");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
	
	@Test
	public void imageTagWithoutDefinedImageProportionShouldConstrainToPageWidthWhenImageIsTooBig() {
		int tooLargeImageWidthInPixels = 2250;
		String result = tag.parse("imagem.png", "[" + tooLargeImageWidthInPixels + "]");
		Assert.assertEquals(
				"\\begin{figure}[H]\n\\centering\n" +
				"\\includegraphics[width=\\textwidth]{imagem.png}\n" +
				"\\end{figure}\n\n", result);
	}
}
