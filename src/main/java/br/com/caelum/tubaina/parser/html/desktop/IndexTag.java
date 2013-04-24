package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.IndexTagTemplate;
import br.com.caelum.tubaina.util.Sanitizer;

import com.google.inject.Inject;

public class IndexTag implements Tag<IndexChunk> {
	private IndexTagTemplate template;
	
	@Inject
	public IndexTag(Sanitizer sanitizer) {
		this.template = new IndexTagTemplate(sanitizer);
	}
	
	@Override
	public String parse(IndexChunk chunk) {
		return template.parse(chunk);
	}

}
