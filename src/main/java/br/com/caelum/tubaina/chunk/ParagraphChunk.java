package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class ParagraphChunk extends AbstractChunk<ParagraphChunk>{

	private final String content;
	private final boolean isInsideItem;
	
	public ParagraphChunk(String content, boolean isInsideItem) {
		this.content = content;
		this.isInsideItem = isInsideItem;
	}

	public String getContent() {
		return content;
	}
	
	public boolean isInsideItem() {
		return isInsideItem;
	}
}
