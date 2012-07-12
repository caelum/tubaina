package br.com.caelum.tubaina.parser.latex;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.SimpleIndentator;

@SuppressWarnings("deprecation")
public class RubyTagTest {
	
	@Test(expected=TubainaException.class)
	public void tagIsDeprecatedAndParsingAgainstItWillAlwaysThrowAnException() {
		RubyTag rubyTag = new RubyTag(new SimpleIndentator(4));
		String code = "# this is a ruby comment";
		rubyTag.parse(code, "");
	}
}
