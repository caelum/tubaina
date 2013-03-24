package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;

public class IndexTagTemplate implements Tag<IndexChunk> {

	@Override
	public String parse(IndexChunk chunk) {
		return "\n<a id=\"" + chunk.getName() + "\"></a>\n";
	}

}
