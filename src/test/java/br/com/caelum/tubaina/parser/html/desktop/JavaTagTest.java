package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.JavaChunk;

@Deprecated
public class JavaTagTest extends AbstractTagTest {

	@Test(expected=Exception.class)
	public void tagIsDeprecatedAndParsingAgainstItWillAlwaysThrowAnException() {
		JavaChunk chunk = new JavaChunk("#", "System.out.println()");
		getContent(chunk);
	}

}
