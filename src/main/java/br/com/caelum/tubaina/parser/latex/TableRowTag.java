package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class TableRowTag implements Tag {

	public String parse(String string, String options) {
		// Remove the & of the last column (put by the TableColumnTag)
		int lastColumnBreak = string.lastIndexOf('&');
		// There IS a column break
		if (lastColumnBreak > 0)
			string = string.substring(0, lastColumnBreak);
		return string + "\\\\";
	}

}
