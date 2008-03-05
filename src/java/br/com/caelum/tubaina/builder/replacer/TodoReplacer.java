package br.com.caelum.tubaina.builder.replacer;

import java.util.regex.Matcher;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.TodoChunk;

public class TodoReplacer extends PatternReplacer {

	private static final Logger LOG = Logger.getLogger(TodoReplacer.class);
	
	public TodoReplacer() {
		super("(?s)(?i)\\A\\s*\\[todo\\s*(.*?)\\]");
	}

	@Override
	public Chunk createChunk(Matcher matcher) {
		String string = matcher.group(1);
		LOG.warn("TODO from chapter " + Chapter.getChaptersCount() + ": " + string);
		return new TodoChunk(string);
	}

}
