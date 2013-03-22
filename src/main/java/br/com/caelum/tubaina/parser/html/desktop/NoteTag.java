package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag {

	public String parse(Chunk chunk) {
		return "<div class=\"note\">" + content.trim() + "</div>";
	}

}
