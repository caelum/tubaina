package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag {

	public String parse(String content, String title) {
		String sanitizedTitle = title != null ? title.trim() : "";
		return "\\begin{tubainabox}{" + sanitizedTitle + "}\n" + content + "\n\\end{tubainabox}";
	}
}
