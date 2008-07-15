package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class TableTag implements Tag {

	private boolean noborder;
	private final Integer columns;
	
	public TableTag(boolean noborder, Integer columns) {
		this.noborder = noborder;
		this.columns = columns;
	}

	public String parse(String string, String options) {
		// TODO Generate a pretty table based on the number of columns
		String result = "";
		if (options != null && options.trim().length() > 0)
			result += "<h3>" + options + "</h3>";
		result += "<table";
		if (!this.noborder)
			result += " border=1";
		result += ">" + string + "</table>";
		return result;
	}

}
