package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class ParagraphInsideItemChunk extends AbstractChunk<ParagraphInsideItemChunk> {
    
    private final String content;

    public ParagraphInsideItemChunk(String content) {
        this.content = content;
    }
    
    public String getContent() {
		return content;
	}
}
