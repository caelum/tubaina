package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class CenteredParagraphTagTemplate implements Tag {

	public String parse(String string, String options) {
		return "<p class=\"center\">" + string + "</p>";
	}

}
