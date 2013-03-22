package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class ItemTagTemplate implements Tag {

	public String parse(Chunk chunk) {
		return "<li>" + string + "</li>";
	}
}
