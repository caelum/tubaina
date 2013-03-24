package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.NoteChunk;
import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag<NoteChunk> {

	@Override
	public String parse(NoteChunk chunk) {
		// The first \n is the title/content sepparator
		
		return "\\begin{tubainanote}\n" +
					chunk.getContent() + "\n" +
				"\\end{tubainanote}";
	}
}
