package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag {

	public String parse(String content, String title) {
		return "\\begin{tubainabox}{" + title.trim() + "}\n" + content + "\n\\end{tubainabox}";
	}
}
