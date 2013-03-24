package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.parser.Tag;

public class IndexTag implements Tag<IndexChunk> {

    @Override
	public String parse(IndexChunk chunk) {
        return "\n\\index{" + escapeUnderscores(chunk.getName()) + "}\n";
    }

    public String escapeUnderscores(String content) {
        return content.replaceAll("\\_", "\\\\_");
    }

}
