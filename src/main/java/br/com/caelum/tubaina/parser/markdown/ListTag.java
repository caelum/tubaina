package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ListTag implements Tag<ListChunk> {

	@Override
	public String parse(ListChunk chunk) {
		return "\n\n" + chunk.getContent() + "\n\n";
	}

}
