package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.ImageChunk;

public class ImageTagTest extends AbstractTagTest {

	private static final String END = "\\end{center}\\end{figure}\n\n";
    private static final String BEGIN = "\\begin{figure}[H]\n\\begin{center}\n";
	
	@Test
	public void testFullImageTag() {
		ImageChunk chunk = new ImageChunk("image.png", "\"Imagem de alguma coisa\" w=30", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=52.5mm]{image.png}\n" +
				"\n\n\\caption{Imagem de alguma coisa}\n\n" +
				END, result);
	}

	@Test
	public void labelAndNoCaption() throws Exception {
		ImageChunk chunk = new ImageChunk("image.png", "label=important", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\\label{important}\n" +
				END, result);
	}
	
	@Test
	public void labelNotInformed() throws Exception {
		ImageChunk chunk = new ImageChunk("image.png", "label=", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\\label{image.png}\n" +
				END, result);
	}
	
	@Test
	public void labelNotInformedFollowedByACaption() throws Exception {
		ImageChunk chunk = new ImageChunk("image.png", "label= \"a caption to the image\"", 100, 1);
		String result = getContent(chunk);
		assertEquals(
		        BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\n\n\\caption{a caption to the image}\n\n" +
				"\\label{image.png}\n" +
				END, result);
	}
	
	@Test
	public void labelAndCaption() throws Exception {
		ImageChunk chunk = new ImageChunk("image.png", "label=important \"a caption to the image\"", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\n\n\\caption{a caption to the image}\n\n" +
				"\\label{important}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithoutBounds() {
		ImageChunk chunk = new ImageChunk("image.png", "\"Imagem de alguma coisa\"", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\n\n\\caption{Imagem de alguma coisa}\n\n" +
				END, result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		ImageChunk chunk = new ImageChunk("image.png", "w=42", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{image.png}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		ImageChunk chunk = new ImageChunk("image.png", "w=40%", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=70.0mm]{image.png}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		ImageChunk chunk = new ImageChunk("image.png", "w=40", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=70.0mm]{image.png}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithInvalidBounds() {
		ImageChunk chunk = new ImageChunk("image.png", "w=42", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{image.png}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithPath() {
		ImageChunk chunk = new ImageChunk("some/path/image.png", "w=42", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{image.png}\n" +
				END, result);
	}
	
	@Test
	public void imageTagWithoutDefinedImageProportionShouldConstrainToPageWidthWhenImageIsTooBig() {
		int tooLargeImageWidthInPixels = 2250;
		ImageChunk chunk = new ImageChunk("image.png", "[" + tooLargeImageWidthInPixels + "]", 100, 1);
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				END, result);
	}
	
	@Test
    public void shouldParseLabelEvenWithStrangeChars() throws Exception {
		ImageChunk chunk = new ImageChunk("image.png", "label=name-with-strange_chars", 100, 1);
		String result = getContent(chunk);
	    assertEquals(
                BEGIN +
                "\\includegraphics[width=\\textwidth]{image.png}\n" +
                "\\label{name-with-strange_chars}\n" +
                END, result);
        
    }
}
