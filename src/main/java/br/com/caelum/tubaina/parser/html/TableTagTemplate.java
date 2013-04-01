package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.Sanitizer;

public class TableTagTemplate implements Tag<TableChunk> {

	private final Sanitizer sanitizer;

	public TableTagTemplate(Sanitizer sanitizer) {
		this.sanitizer = sanitizer;
	}

	@Override
	public String parse(TableChunk chunk) {
		String result = "";
		String title = sanitizer.sanitize(chunk.getTitle());
		if (title != null && !title.trim().isEmpty())
			result += "<h3>" + title + "</h3>";
		result += "<table";
		if (chunk.hasBorder())
			result += " border=1";
		result += ">" + chunk.getContent() + "</table>";
		return result;
	}

}
