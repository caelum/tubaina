package br.com.caelum.tubaina.parser.latex;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.html.desktop.XmlTag;

@SuppressWarnings("deprecation")
public class XmlTagTest {

	@Test(expected=TubainaException.class)
	public void tagIsDeprecatedAndParsingAgainstItWillAlwaysThrowAnException() {
		new XmlTag(new SimpleIndentator(4)).parse("<an xml>", "");
	}
	
}
