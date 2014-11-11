package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.Sanitizer;
import org.apache.log4j.Logger;

public class IndexTagTemplate implements Tag<IndexChunk> {

	private final Sanitizer sanitizer;
	private static final Logger LOG = Logger.getLogger(IndexTagTemplate.class);

	public IndexTagTemplate(Sanitizer sanitizer) {
		this.sanitizer = sanitizer;
	}

	@Override
	public String parse(IndexChunk chunk) {
		String index = sanitizer.sanitize(chunk.getName());
		LOG.warn("HTML format doesn't support [index] tag");
		return "";
	}

}
