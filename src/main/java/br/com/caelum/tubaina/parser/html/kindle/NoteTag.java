package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag {

	public String parse(Chunk chunk) {
		return "---------------------------<br />" + content.trim() + "<br />---------------------------";
	}

}
