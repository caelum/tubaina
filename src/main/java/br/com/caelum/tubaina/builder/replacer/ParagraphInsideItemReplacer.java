package br.com.caelum.tubaina.builder.replacer;

import java.util.regex.Matcher;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.ParagraphInsideItemChunk;

public class ParagraphInsideItemReplacer extends ParagraphReplacer {

	public ParagraphInsideItemReplacer(String specialTerms) {
        super(specialTerms);
    }

	@Override
    public Chunk createChunk(Matcher matcher) {
		return new ParagraphInsideItemChunk(matcher.group(1));
	}

}
