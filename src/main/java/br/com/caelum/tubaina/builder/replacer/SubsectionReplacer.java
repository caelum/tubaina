package br.com.caelum.tubaina.builder.replacer;

import java.util.regex.Matcher;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.SubsectionChunk;

public class SubsectionReplacer extends PatternReplacer {

	public SubsectionReplacer() {
		super("(?s)(?i)\\[subsection (.+?)\\]");
	}

	@Override
	public Chunk createChunk(Matcher matcher) {
		String title = matcher.group(1);
		return new SubsectionChunk(title);
	}

}
