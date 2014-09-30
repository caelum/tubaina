package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.chunk.ImageChunk;

public class ImageTagTest extends AbstractTagTest {

	private static final String NO_CAPTION = "\n\n\\captionsetup{labelsep=none, labelformat=empty}\\caption{}\n\n";
	private static final String RENEW_COMMAND = "\n\n\\renewcommand{\\figurename}{Fig.}";
	private static final String END = "\\end{center}\\end{figure}\n\n";
	private static final String BEGIN = "\n\n\\begin{figure}[H]\n\\begin{center}\n";
	
	@Test
	public void testFullImageTag() {
		ImageChunk chunk = makeChunk("image.png", "\"Imagem de alguma coisa\" w=30");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=52.5mm]{image.png}\n" +
				RENEW_COMMAND +
				"\n\\caption{Imagem de alguma coisa}\n\n" +
				END, result);
	}

	@Test
	public void labelAndNoCaption() throws Exception {
		ImageChunk chunk = makeChunk("image.png", "label=important");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				NO_CAPTION +
				"\\label{important}\n" +
				END, result);
	}
	
	@Test
	public void labelNotInformed() throws Exception {
		ImageChunk chunk = makeChunk("image.png", "label=");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				NO_CAPTION +
				"\\label{image.png}\n" +
				END, result);
	}

	@Test
	public void labelNotInformedFollowedByACaption() throws Exception {
		ImageChunk chunk = makeChunk("image.png", "label= \"a caption to the image\"");
		String result = getContent(chunk);
		assertEquals(
		        BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				RENEW_COMMAND +
				"\n\\caption{a caption to the image}\n\n" +
				"\\label{image.png}\n" +
				END, result);
	}
	
	@Test
	public void labelAndCaption() throws Exception {
		ImageChunk chunk = makeChunk("image.png", "label=important \"a caption to the image\"");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				RENEW_COMMAND +
				"\n\\caption{a caption to the image}\n\n" +
				"\\label{important}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithoutBounds() {
		ImageChunk chunk = makeChunk("image.png", "\"Imagem de alguma coisa\"");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				RENEW_COMMAND +
				"\n\\caption{Imagem de alguma coisa}\n\n" +
				END, result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		ImageChunk chunk = makeChunk("image.png", "w=42");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{image.png}\n" +
				NO_CAPTION +
				END, result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		ImageChunk chunk = makeChunk("image.png", "w=40%");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=70.0mm]{image.png}\n" +
				NO_CAPTION +
				END, result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		ImageChunk chunk = makeChunk("image.png", "w=40");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=70.0mm]{image.png}\n" +
				NO_CAPTION +
				END, result);
	}
	
	@Test
	public void testImageTagWithInvalidBounds() {
		ImageChunk chunk = makeChunk("image.png", "w=42");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{image.png}\n" +
				NO_CAPTION +
				END, result);
	}
	
	@Test
	public void testImageTagWithPath() {
		ImageChunk chunk = makeChunk("some/path/image.png", "w=42");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{image.png}\n" +
				NO_CAPTION +
				END, result);
	}

	
	@Test
	public void imageTagWithoutDefinedImageProportionShouldConstrainToPageWidthWhenImageIsTooBig() {
		int tooLargeImageWidthInPixels = 2250;
		ImageChunk chunk = makeChunk("image.png", "[" + tooLargeImageWidthInPixels + "]");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				NO_CAPTION +
				END, result);
	}
	
	@Test
	public void shouldParseLabelEvenWithStrangeChars() throws Exception {
		ImageChunk chunk = makeChunk("image.png", "label=name-with-strange_chars");
		String result = getContent(chunk);
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				NO_CAPTION +
				"\\label{name-with-strange_chars}\n" +
				END, result);
	}
	
	@Test
	public void shouldParseTagsInsideSubtitle() {
		ImageChunk chunk = makeChunk("blabla.png", "\"lala **bold text** http://caelum.com.br/ \""); 
		String output = getContent(chunk);
		assertTrue(output.contains("\\url"));
		assertTrue(output.contains("\\definition"));
	}
	
	private ImageChunk makeChunk(String path, String options) {
		ImageChunk chunk = new ImageChunk(path, options, 100, 1, new SectionsManager());
		return chunk;
	}
}
