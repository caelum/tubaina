package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.RubyChunk;

@Deprecated
public class RubyTagTest extends AbstractTagTest {

	@Test(expected = Exception.class)
	public void tagIsDeprecatedAndParsingAgainstItWillAlwaysThrowAnException() {
		RubyChunk chunk = new RubyChunk("# this is a ruby comment", "#");
		getContent(chunk);
	}
}
