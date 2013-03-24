package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class MockedChunk extends AbstractChunk<MockedChunk> {

	private String content;

	public MockedChunk(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}
