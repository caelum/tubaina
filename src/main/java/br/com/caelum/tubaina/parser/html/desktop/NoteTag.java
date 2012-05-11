package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag {

	public String parse(String content, String options) {
		return "<div class=\"note\">" + content.trim() + "</div>";
	}

}
