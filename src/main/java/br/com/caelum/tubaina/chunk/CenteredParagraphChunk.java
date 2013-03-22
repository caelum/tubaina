package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class CenteredParagraphChunk extends AbstractChunk<CenteredParagraphChunk> {

	private final String content;

	public CenteredParagraphChunk(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
