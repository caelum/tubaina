package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class RefChunk implements Chunk {

    private String content;

    public RefChunk(String content) {
        this.content = content;
    }

    public String getContent(Parser p) {
        return p.parseRef(this.content);
    }

}
