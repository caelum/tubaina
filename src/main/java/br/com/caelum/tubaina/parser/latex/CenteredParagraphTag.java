package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class CenteredParagraphTag implements Tag {

	public String parse(String string, String options) {
		return "\\begin{center}" + string + "\\end{center}";
	}

}
