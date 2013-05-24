package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.SubsectionChunk;

public class SubsectionTagTest extends AbstractTagTest {

	@Test
	public void shouldParseSubsection() {
		Chunk chunk = new SubsectionChunk("title", text("content"), 1, 1, 1);
		String content = getContent(chunk);
		assertEquals("\\subsection{title}\ncontent", content);
	}
	
}
