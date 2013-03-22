package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.SimpleIndentator;

@SuppressWarnings("deprecation")
public class JavaTagTest {

	@Test(expected=TubainaException.class)
	public void tagIsDeprecatedAndParsingAgainstItWillAlwaysThrowAnException() {
		JavaTag tag = new JavaTag(new SimpleIndentator(4));
		tag.parse(chunk);
	}

}
