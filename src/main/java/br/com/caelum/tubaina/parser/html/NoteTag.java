package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag {

	public String parse(String content, String options) {
		return "<div class=\"note\">" + content.trim() + "</div>";
	}

}
