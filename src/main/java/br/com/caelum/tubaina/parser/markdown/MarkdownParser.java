package br.com.caelum.tubaina.parser.markdown;

import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexTag;

public class MarkdownParser implements Parser {

	private final List<RegexTag> tags;

	public MarkdownParser(List<RegexTag> tags) {
		this.tags = tags;
	}

	@Override
	public String parse(String string) {
		for (RegexTag tag : tags) {
			string = tag.parse(string);
		}
		return string;
	}
}