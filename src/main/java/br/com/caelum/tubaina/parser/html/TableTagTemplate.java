package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableTagTemplate implements Tag<TableChunk> {

	@Override
	public String parse(TableChunk chunk) {
		String result = "";
		String title = chunk.getTitle();
		if (title != null && !title.trim().isEmpty())
			result += "<h3>" + title + "</h3>";
		result += "<table";
		if (chunk.hasBorder())
			result += " border=1";
		result += ">" + chunk.getContent() + "</table>";
		return result;
	}

}
