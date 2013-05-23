package br.com.caelum.tubaina.parser.html.kindle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.SubsectionChunk;

public class SubsectionTagTest {

	@Test
	public void shouldParseSubsectionChunk() {
		SubsectionTag subsectionTag = new SubsectionTag();
		SubsectionChunk chunk = new SubsectionChunk("title", 1, 1, 1);
		String parsed = subsectionTag.parse(chunk);
		assertEquals("<h3>1.1.1 - title</h3>", parsed);
	}

}
