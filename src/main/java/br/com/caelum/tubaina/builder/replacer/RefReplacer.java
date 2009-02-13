package br.com.caelum.tubaina.builder.replacer;

import java.util.regex.Matcher;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.RefChunk;

public class RefReplacer extends PatternReplacer {

    public RefReplacer() {
        super("(?s)(?i)\\A\\s*\\[ref\\s*(.*?)(\\s*,\\s*.*)*\\]");
    }

    @Override
    public Chunk createChunk(Matcher matcher) {
        String string = matcher.group(1);
        return new RefChunk(string);
    }
}
