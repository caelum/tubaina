package br.com.caelum.tubaina.parser;

import br.com.caelum.tubaina.chunk.NoteChunk;

public class NullNoteTag implements Tag<NoteChunk> {

	@Override
	public String parse(NoteChunk chunk) {
		return "";
	}

}
