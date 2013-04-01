package br.com.caelum.tubaina.parser.html.kindle;

import com.google.inject.Inject;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.IndexTagTemplate;
import br.com.caelum.tubaina.util.Sanitizer;

public class IndexTag implements Tag<IndexChunk> {
	private final IndexTagTemplate template;

	@Inject
	public IndexTag(Sanitizer sanitizer) {
		this.template = new IndexTagTemplate(sanitizer);
	}
	
	@Override
	public String parse(IndexChunk chunk) {
		return template.parse(chunk);
	}
}
