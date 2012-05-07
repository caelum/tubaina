package br.com.caelum.tubaina.builder.replacer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.ParagraphInsideItemChunk;

public class ParagraphInsideItemReplacer implements Replacer {

	private final Pattern pattern;
	
	private final Pattern tagPattern = Pattern.compile("(?s)(?i)\\A\\[(.+)\\].*\\[/\\1\\]");
	
	public ParagraphInsideItemReplacer(String specialTerms) {
		this.pattern = Pattern.compile("(?s)(?i)\\A\\s*(.+?)(\\n(\\s)*?\\n|\\s*\\[(" +
				specialTerms +
				")|\\s*\\z)");
	}

	public String execute(String text, List<Chunk> chunks) {
		Matcher matcher = pattern.matcher(text);
		matcher.find();
		chunks.add(createChunk(matcher));
		return text.substring(matcher.end(1));
	}

	public boolean accepts(String text) {
		Matcher tagMatcher = tagPattern.matcher(text);
		if (tagMatcher.find())
			return false;
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			if (matcher.group().trim().length() > 0) {
				return true;
			}
		}
		return false;
	}

	public Chunk createChunk(Matcher matcher) {
		return new ParagraphInsideItemChunk(matcher.group(1));
	}

}
