package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.NoteChunk;
import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag<NoteChunk> {

	@Override
	public String parse(NoteChunk chunk) {
		return "<div class=\"note\">" + chunk.getContent().trim() + "</div>";
	}

}
