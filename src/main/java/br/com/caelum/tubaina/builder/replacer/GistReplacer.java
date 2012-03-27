package br.com.caelum.tubaina.builder.replacer;

import java.util.regex.Matcher;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.GistChunk;

public class GistReplacer extends PatternReplacer {

	public GistReplacer() {
		super("(?s)(?i)\\A\\s*\\[gist \\s*(.*?)\\s*\\]");
	}

	@Override
	public Chunk createChunk(Matcher matcher) {
		return new GistChunk(matcher.group(1));
	}

}
