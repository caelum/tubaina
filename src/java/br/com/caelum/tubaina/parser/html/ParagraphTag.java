package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class ParagraphTag implements Tag {

	public String parse(String string, String options) {
		return "<span class=\"paragraph\">" + string + "</span>";
	}

}
