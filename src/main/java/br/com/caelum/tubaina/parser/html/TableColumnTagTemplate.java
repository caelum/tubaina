package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableColumnTagTemplate implements Tag {

	public String parse(Chunk chunk) {
		return "<td>" + text + "</td>";
	}

}
