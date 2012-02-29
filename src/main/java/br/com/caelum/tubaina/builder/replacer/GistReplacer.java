package br.com.caelum.tubaina.builder.replacer;

import java.util.regex.Matcher;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.GistChunk;

public class GistReplacer extends PatternReplacer {

	private static final Logger LOG = Logger.getLogger(GistReplacer.class);

	public GistReplacer() {
		super("(?s)(?i)\\A\\s*\\[gist \\s*(.*?)\\s*\\]");
	}

	@Override
	public Chunk createChunk(Matcher matcher) {
		return new GistChunk(matcher.group(1));
	}

}
