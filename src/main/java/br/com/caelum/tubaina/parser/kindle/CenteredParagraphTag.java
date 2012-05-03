package br.com.caelum.tubaina.parser.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class CenteredParagraphTag implements Tag {

	public String parse(String string, String options) {
		return "<p class=\"center\">" + string + "</p>";
	}

}
