package br.com.caelum.tubaina.parser.html.desktop;

import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexTag;

public class HtmlParser implements Parser {

	private final List<RegexTag> tags;

	public HtmlParser(List<RegexTag> tags) {
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