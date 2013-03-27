package br.com.caelum.tubaina.parser.html.kindle;

import com.google.inject.Inject;

import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableTagTemplate;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class TableTag implements Tag<TableChunk> {

	private TableTagTemplate template;

	@Inject
	public TableTag(HtmlSanitizer sanitizer) {
		template = new TableTagTemplate(sanitizer);
	}

	@Override
	public String parse(TableChunk chunk) {
		return template.parse(chunk);
	}

}
