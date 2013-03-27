package br.com.caelum.tubaina.parser.html.kindle;

import com.google.inject.Inject;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.IndexTagTemplate;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class IndexTag implements Tag<IndexChunk> {
	private final IndexTagTemplate template;

	@Inject
	public IndexTag(HtmlSanitizer sanitizer) {
		this.template = new IndexTagTemplate(sanitizer);
	}
	
	@Override
	public String parse(IndexChunk chunk) {
		return template.parse(chunk);
	}
}
