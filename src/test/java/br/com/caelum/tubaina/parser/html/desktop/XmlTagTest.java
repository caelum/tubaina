package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.XmlChunk;

@Deprecated
public class XmlTagTest extends AbstractTagTest {

	@Test(expected=Exception.class)
	public void tagIsDeprecatedAndParsingAgainstItWillAlwaysThrowAnException() {
		XmlChunk chunk = new XmlChunk("#", "<a>tag</a>");
		getContent(chunk);
	}
	
}