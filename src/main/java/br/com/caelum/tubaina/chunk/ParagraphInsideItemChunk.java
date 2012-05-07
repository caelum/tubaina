package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class ParagraphInsideItemChunk implements Chunk {
    
    private final String content;

    public ParagraphInsideItemChunk(String content) {
        this.content = content;
    }
    
    public String getContent(Parser p) {
        return p.parseParagraphInsideItem(content);
    }

}
