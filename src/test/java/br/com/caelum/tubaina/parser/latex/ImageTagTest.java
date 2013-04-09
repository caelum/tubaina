package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.ParseType;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.Tag;

public class ImageTagTest {

	private static final String END = "\\end{center}\\end{figure}\n\n";
    private static final String BEGIN = "\n\n\\begin{figure}[H]\n\\begin{center}\n";
    private Tag tag;

	@Before
	public void setUp() throws IOException {
		RegexConfigurator configurator = new RegexConfigurator();
        Parser parser = ParseType.LATEX.getParser(configurator, false, false, "");
		tag = new ImageTag(parser);
	}
	
	@Test
	public void testFullImageTag() {
		String result = tag.parse("imagem.png", "w=30 \"Imagem de alguma coisa\"");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=52.5mm]{imagem.png}\n" +
				"\n\n\\caption{Imagem de alguma coisa}\n\n" +
				END, result);
	}

	@Test
	public void labelAndNoCaption() throws Exception {
		String result = tag.parse("image.png", "label=important");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\\label{important}\n" +
				END, result);
	}
	
	@Test
	public void labelNotInformed() throws Exception {
		String result = tag.parse("image.png", "label=");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\\label{image.png}\n" +
				END, result);
	}
	
	@Test
	public void labelNotInformedFollowedByACaption() throws Exception {
		String result = tag.parse("image.png", "label= \"a caption to the image\"");
		assertEquals(
		        BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\n\n\\caption{a caption to the image}\n\n" +
				"\\label{image.png}\n" +
				END, result);
	}
	
	@Test
	public void labelAndCaption() throws Exception {
		String result = tag.parse("image.png", "label=important \"a caption to the image\"");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{image.png}\n" +
				"\n\n\\caption{a caption to the image}\n\n" +
				"\\label{important}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithoutBounds() {
		String result = tag.parse("imagem.png", "\"Imagem de alguma coisa\"");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{imagem.png}\n" +
				"\n\n\\caption{Imagem de alguma coisa}\n\n" +
				END, result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		String result = tag.parse("imagem.png", "w=42");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		String result = tag.parse("imagem.png", "w=40%");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=70.0mm]{imagem.png}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		String result = tag.parse("imagem.png", "w=40");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=70.0mm]{imagem.png}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithInvalidBounds() {
		String result = tag.parse("imagem.png", "w=42");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				END, result);
	}
	
	@Test
	public void testImageTagWithPath() {
		String result = tag.parse("some/path/imagem.png", "w=42");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=73.5mm]{imagem.png}\n" +
				END, result);
	}
	
	@Test
	public void imageTagWithoutDefinedImageProportionShouldConstrainToPageWidthWhenImageIsTooBig() {
		int tooLargeImageWidthInPixels = 2250;
		String result = tag.parse("imagem.png", "[" + tooLargeImageWidthInPixels + "]");
		assertEquals(
				BEGIN +
				"\\includegraphics[width=\\textwidth]{imagem.png}\n" +
				END, result);
	}
	
	@Test
    public void shouldParseLabelEvenWithStrangeChars() throws Exception {
	    String result = tag.parse("image.png", "label=name-with-strange_chars");
	    assertEquals(
                BEGIN +
                "\\includegraphics[width=\\textwidth]{image.png}\n" +
                "\\label{name-with-strange_chars}\n" +
                END, result);
        
    }
	
	@Test
    public void shouldParseTagsInsideSubtitle() {
    	String output = tag.parse("blabla.png", "\"lala **bold text** http://caelum.com.br/ \"");
    	output.contains("\\url");
    	output.contains("\\definition");
    }
}
