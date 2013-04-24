package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.TableTagTemplate;
import br.com.caelum.tubaina.util.Sanitizer;

import com.google.inject.Inject;

public class TableTag implements Tag<TableChunk> {

	private TableTagTemplate template;

	@Inject
	public TableTag(Sanitizer sanitizer) {
		template = new TableTagTemplate(sanitizer);
	}

	@Override
	public String parse(TableChunk chunk) {
		return template.parse(chunk);
	}

}
