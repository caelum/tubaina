package br.com.caelum.tubaina.parser.latex;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.RubyChunk;
import br.com.caelum.tubaina.parser.SimpleIndentator;

@Deprecated
public class RubyTagTest extends AbstractTagTest {
	
	@Test(expected=Exception.class)
	public void tagIsDeprecatedAndParsingAgainstItWillAlwaysThrowAnException() {
		RubyTag rubyTag = new RubyTag(new SimpleIndentator(4));
		String code = "# this is a ruby comment";
		RubyChunk chunk = new RubyChunk(code, "");
		rubyTag.parse(chunk);
	}
}
