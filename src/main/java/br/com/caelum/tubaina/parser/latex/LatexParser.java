package br.com.caelum.tubaina.parser.latex;

import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexTag;

public class LatexParser implements Parser {

	private final List<RegexTag> tags;

	public LatexParser(List<RegexTag> tags) {
		this.tags = tags;
	}

	@Override
	public String parse(String string) {
		// TODO: remove eventual $1, $2 from the string so as not to be interpreted
		string = new EscapeTag().parse(string);
		for (RegexTag tag : tags) {
			string = tag.parse(string);
		}
		return string;
	}
}