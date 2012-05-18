package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.SimpleIndentator;

@SuppressWarnings("deprecation")
public class XmlTagTest {

	@Test(expected=TubainaException.class)
	public void tagIsDeprecatedAndParsingAgainstItWillAlwaysThrowAnException() {
		new XmlTag(new SimpleIndentator()).parse("<an xml>", "");
	}
	
}