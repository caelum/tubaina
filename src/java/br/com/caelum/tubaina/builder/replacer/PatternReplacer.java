package br.com.caelum.tubaina.builder.replacer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chunk;

public abstract class PatternReplacer implements Replacer {

	private final Pattern pattern;

	public PatternReplacer(String patternString) {
		this.pattern = Pattern.compile(patternString);
	}

	public String execute(String text, List<Chunk> chunks) {
		Matcher matcher = pattern.matcher(text);
		matcher.find();
		chunks.add(createChunk(matcher));
		return text.substring(matcher.end());
	}

	public abstract Chunk createChunk(Matcher matcher);

	public boolean accepts(String text) {
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}

}
