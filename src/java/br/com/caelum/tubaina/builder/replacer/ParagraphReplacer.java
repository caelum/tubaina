package br.com.caelum.tubaina.builder.replacer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.ParagraphChunk;

public class ParagraphReplacer implements Replacer {

	private final Pattern pattern;

	public ParagraphReplacer(String specialTerms) {
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
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			if (matcher.group().trim().length() > 0) {
				return true;
			}
		}
		return false;
	}

	public Chunk createChunk(Matcher matcher) {
		return new ParagraphChunk(matcher.group(1));
	}

}
