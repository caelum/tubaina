package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;

public class IndexTag implements Tag<IndexChunk> {

    @Override
	public String parse(IndexChunk chunk) {
        return "";
    }

}
