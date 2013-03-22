package br.com.caelum.tubaina.parser;

import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chunk;

public class RegexTag implements Tag {
	private String replacement;

	private Pattern regex;

	public RegexTag(String regex, String replacement) {
		this.regex = Pattern.compile(regex);
		this.replacement = replacement;
	}

	public String parse(Chunk chunk) {
		String out = regex.matcher(string).replaceAll(replacement);
        return out;
	}

	@Override
	public String toString() {
		return String.format("[regex %s %s]", regex, replacement);
	}
}
