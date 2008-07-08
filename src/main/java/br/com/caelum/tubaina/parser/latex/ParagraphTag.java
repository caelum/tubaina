package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class ParagraphTag implements Tag {

	public String parse(String string, String options) {
		return "\n\n" + string;
	}

}
