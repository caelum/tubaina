package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.Sanitizer;

public class IndexTagTemplate implements Tag<IndexChunk> {

	private final Sanitizer sanitizer;

	public IndexTagTemplate(Sanitizer sanitizer) {
		this.sanitizer = sanitizer;
	}

	@Override
	public String parse(IndexChunk chunk) {
		String index = sanitizer.sanitize(chunk.getName());
		return "\n<a id=\"" + index + "\"></a>\n";
	}

}
