package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.IndexTagTemplate;

public class IndexTag implements Tag<IndexChunk> {
	private IndexTagTemplate template = new IndexTagTemplate();

	@Override
	public String parse(IndexChunk chunk) {
		return template.parse(chunk);
	}
}
