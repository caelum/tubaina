package br.com.caelum.tubaina.parser.html;



import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ImageTagTemplateTest {

	private ImageTagTemplate tag;
	private String imageWithSubtitle;
	private String imageWithoutSubtitle;
	private String imageWithWidth;

	@Before
	public void setUp() {
		tag = new ImageTagTemplate();
		imageWithSubtitle = "<img src=\"$$RELATIVE$$/imagem.png\" id=\"imagem.png\" alt=\"Imagem de alguma coisa\" />\n<div>Imagem de alguma coisa</div><br><br>";
		imageWithoutSubtitle = "<img src=\"$$RELATIVE$$/imagem.png\" id=\"imagem.png\" alt=\"imagem.png\" />";
		imageWithWidth = "<img src=\"$$RELATIVE$$/imagem.png\" id=\"imagem.png\" width='50%' alt=\"imagem.png\" />";
	}

	@Test
	public void testFullImageTag() {
		String result = tag.parse("imagem.png", "w=30 \"Imagem de alguma coisa\"");
		assertEquals(imageWithSubtitle, result);
	}

	@Test
	public void testImageTagWithoutBounds() {
		String result = tag.parse("imagem.png", "\"Imagem de alguma coisa\"");
		assertEquals(imageWithSubtitle, result);
	}

	@Test
	public void testImageTagWithoutDesc() {
		String result = tag.parse("imagem.png", "w=42");
		assertEquals(imageWithoutSubtitle, result);
	}
	
	@Test
	public void testImageTagWithPath() {
		String result = tag.parse("some/path/imagem.png", "w=42");
		assertEquals(imageWithoutSubtitle, result);
	}
	
	@Test
	public void testImageTagWithPercentageSymbol() {
		String result = tag.parse("some/path/imagem.png", "w=50%");
		assertEquals(imageWithoutSubtitle, result);
	}
	
	@Test
	public void testImageTagWithoutPercentageSymbol() {
		String result = tag.parse("some/path/imagem.png", "w=50", true);
		assertEquals(imageWithWidth, result);
	}
	
	@Test
    public void shouldUseHtmlWidth() throws Exception {
	    String result = tag.parse("imagem.png", "w=50", true);
		assertEquals(imageWithWidth, result);
    }
	
	@Test
	public void shouldUseLabelAsId() throws Exception {
	    String result = tag.parse("imagem.png", "w=50 label=image-label", true);
	    String imageUsingLabelAsId = "<img src=\"$$RELATIVE$$/imagem.png\" id=\"image-label\" width='50%' alt=\"imagem.png\" />";
		assertEquals(imageUsingLabelAsId, result);
	}
	
	@Test
	public void shouldUsePathAsIdWhenLabelTheresNoLabel() throws Exception {
	    String result = tag.parse("imagem.png", "w=50", true);
	    assertEquals(imageWithWidth, result);
	}
}
