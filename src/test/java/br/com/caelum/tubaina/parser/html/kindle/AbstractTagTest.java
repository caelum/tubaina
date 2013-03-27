package br.com.caelum.tubaina.parser.html.kindle;

import java.util.Arrays;
import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.MockedChunk;

public class AbstractTagTest {

	protected String getContent(Chunk chunk) {
		new KindleModule().inject(chunk);
		return chunk.asString();
	}

	protected List<Chunk> text(String text) {
		return Arrays.<Chunk>asList(new MockedChunk(text));
	}
}
