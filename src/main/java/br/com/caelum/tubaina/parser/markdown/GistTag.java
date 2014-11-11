package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.GistChunk;
import br.com.caelum.tubaina.parser.Tag;

public class GistTag implements Tag<GistChunk> {

    @Override
	public String parse(GistChunk chunk) {
    	throw new TubainaException("gist not supported");
    }
    
}
