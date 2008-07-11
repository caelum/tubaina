package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class TableColumnTag implements Tag {

	public String parse(String text, String options) {
		return "<td>" + text + "</td>";
	}

}
