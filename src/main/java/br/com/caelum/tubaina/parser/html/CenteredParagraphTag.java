package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class CenteredParagraphTag implements Tag {

	public String parse(String string, String options) {
		return "<div style=\"text-align:center\">" + string + "</div>";
	}

}
