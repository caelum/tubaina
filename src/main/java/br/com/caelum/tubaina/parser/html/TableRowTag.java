package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class TableRowTag implements Tag {

	public String parse(String string, String options) {
		return "<tr>" + string + "</tr>";
	}

}
