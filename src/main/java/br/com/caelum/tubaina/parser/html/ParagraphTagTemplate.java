package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class ParagraphTagTemplate implements Tag {

	public String parse(Chunk chunk) {
		return "<p>" + string + "</p>";
	}

}
