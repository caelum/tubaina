package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class MockedChunk implements Chunk {

	private String content;

	public MockedChunk(String content) {
		this.content = content;
	}
	
	public String getContent(Parser p) {
		return content;
	}

}
