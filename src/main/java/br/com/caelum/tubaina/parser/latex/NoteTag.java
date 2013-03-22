package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag {

	public String parse(Chunk chunk) {
		// The first \n is the title/content sepparator
		
		return "\\begin{tubainanote}\n" +
					string + "\n" +
				"\\end{tubainanote}";
	}
}
