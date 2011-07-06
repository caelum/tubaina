package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class TableTag implements Tag {

	private boolean noborder;
	
	public TableTag(boolean noborder) {
		this.noborder = noborder;
	}

	public String parse(String string, String options) {
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
