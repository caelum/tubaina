package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;

public class MockedChunk implements Chunk {

	private String content;

	public MockedChunk(String content) {
		this.content = content;
	}

	@Override
	public String asString() {
		return content;
	}
}
