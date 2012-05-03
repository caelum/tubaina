package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.html.desktop.JavaTag;
import de.java2html.Java2Html;
import de.java2html.JavaSourceConversionSettings;
import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSource2Xhtml11Converter;
import de.java2html.options.JavaSourceConversionOptions;

public class JavaTagTest {

	private JavaSourceConversionSettings settings;

	@Before
	public void setUp() {
		JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
		
		settings = new JavaSourceConversionSettings(options) {
			@Override
			public IJavaSourceConverter createConverter() {
				return new JavaSource2Xhtml11Converter();
			}
		};
		
	}
	@Test
	public void testOneSimpleTag() {
		JavaTag tag = new JavaTag(new SimpleIndentator());
		String result = tag.parse("\nclass", "");
		String j2hString = Java2Html.convertToHtml("class", settings);
		j2hString = j2hString.replaceAll("<!--(.+?)-->\n", "").replaceFirst("<div class=\"java\"><code class=\"java\">", "$0\n").trim();
		Assert.assertEquals(j2hString, result);
	}

	@Test
	public void testNumberedTag() {
		JavaTag tag = new JavaTag(new SimpleIndentator());
		String result = tag.parse("class", "#");
		settings.getConversionOptions().setShowLineNumbers(true);
		String j2hString = Java2Html.convertToHtml("class", settings);
		j2hString = j2hString.replaceAll("<!--(.+?)-->\n", "").replaceFirst("<div class=\"java\"><code class=\"java\">", "$0\n").trim();
		Assert.assertEquals(j2hString, result);
	}

}
