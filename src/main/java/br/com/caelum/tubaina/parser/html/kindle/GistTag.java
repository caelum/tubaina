package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.GistChunk;
import br.com.caelum.tubaina.parser.Tag;

public class GistTag implements Tag<GistChunk> {

	@Override
	public String parse(GistChunk chunk) {
		// TODO - Support gist at HTML
		return "";
	}

}
