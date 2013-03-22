package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class ParagraphTag implements Tag {

	public String parse(Chunk chunk) {
		return "\n\n" + string;
	}

}
