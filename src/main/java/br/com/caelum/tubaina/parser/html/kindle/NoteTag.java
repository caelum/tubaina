package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag {

	public String parse(String content, String options) {
		return "<hr/>" + content.trim() + "<hr/>";
	}

}
