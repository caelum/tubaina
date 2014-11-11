package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.NoteChunk;
import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag<NoteChunk> {

	@Override
	public String parse(NoteChunk chunk) {
		return "";
	}
}
