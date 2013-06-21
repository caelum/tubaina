package br.com.caelum.tubaina.parser.html;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.ParseType;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Parser;

@SuppressWarnings("deprecation")
public class ImageTagTemplateTest {

	private ImageTagTemplate tag;
	private String imageWithSubtitle;
	private String imageWithoutSubtitle;
	private String imageWithWidth;

	@Before
	public void setUp() throws IOException {
		Parser parser = ParseType.HTML.getParser();
		tag = new ImageTagTemplate(parser);
		imageWithSubtitle = "<img src=\"$$RELATIVE$$/imagem.png\" id=\"imagem.png\" alt=\"Imagem de alguma coisa\" />\n<div><i>Figura 0.1: Imagem de alguma coisa</i></div><br><br>";
		imageWithoutSubtitle = "<img src=\"$$RELATIVE$$/imagem.png\" id=\"imagem.png\" alt=\"imagem.png\" />";
		imageWithWidth = "<img src=\"$$RELATIVE$$/imagem.png\" id=\"imagem.png\" width='50%' alt=\"imagem.png\" />";
	}

	@Test
	public void testFullImageTag() {
		ChapterBuilder.restartChapterCounter();
		ImageChunk chunk = makeChunk("imagem.png", "w=30 \"Imagem de alguma coisa\"", 30);
		String result = tag.parse(chunk, false);
		assertEquals(imageWithSubtitle, result);
	}

	@Test
	public void testImageTagWithoutBounds() {
		ChapterBuilder.restartChapterCounter();
		ImageChunk chunk = makeChunk("imagem.png", "\"Imagem de alguma coisa\"", 100);
		String result = tag.parse(chunk, false);
		assertEquals(imageWithSubtitle, result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		ImageChunk chunk = makeChunk("imagem.png", "w=42", 42);
		String result = tag.parse(chunk, false);
		assertEquals(imageWithoutSubtitle, result);
	}
	
	@Test
	public void testImageTagWithPath() {
		ImageChunk chunk = makeChunk("some/path/imagem.png", "w=42", 42);
		String result = tag.parse(chunk, false);
		assertEquals(imageWithoutSubtitle, result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		ImageChunk chunk = makeChunk("some/path/imagem.png", "w=50%", 50);
		String result = tag.parse(chunk, false);
		assertEquals(imageWithoutSubtitle, result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		ImageChunk chunk = makeChunk("some/path/imagem.png", "w=50", 50);
		String result = tag.parse(chunk, true);
		assertEquals(imageWithWidth, result);
	}
	
	@Test
	public void shouldUseLabelAsId() throws Exception {
		ImageChunk chunk = makeChunk("imagem.png", "w=50 label=image-label", 50);
		String result = tag.parse(chunk, true);
	    String imageUsingLabelAsId = "<img src=\"$$RELATIVE$$/imagem.png\" id=\"image-label\" width='50%' alt=\"imagem.png\" />";
		assertEquals(imageUsingLabelAsId, result);
	}
	
	@Test
	public void shouldParseTagsInsideCaption() throws Exception {
		ImageChunk chunk = makeChunk("imagem.png", "\"Configurações de zoom do Android 4 e do **Chrome** http://google.com.br/ Mobile\"", 50);
		String result = tag.parse(chunk, true);
		result.contains("<strong>");
		result.contains("href=\"http://google.com/\"");
	}
	
	private ImageChunk makeChunk(String path, String options, int width) {
		return new ImageChunk(path, options, width, 1, new SectionsManager());
	}
}
