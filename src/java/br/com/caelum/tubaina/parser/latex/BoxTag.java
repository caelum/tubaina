package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag {

	public String parse(String content, String title) {
		return "\\boxtag{" + title.trim() + "}{" + content + "}";
	}
}
