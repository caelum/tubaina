package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableTagTemplate implements Tag<TableChunk> {

	private boolean noborder;
	
	public TableTagTemplate(boolean noborder) {
		this.noborder = noborder;
	}

	@Override
	public String parse(TableChunk chunk) {
		String result = "";
		String title = chunk.getTitle();
		if (title != null && !title.trim().isEmpty())
			result += "<h3>" + title + "</h3>";
		result += "<table";
		if (!this.noborder)
			result += " border=1";
		result += ">" + chunk.getContent() + "</table>";
		return result;
	}

}
