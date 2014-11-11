package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ItemTag implements Tag<ItemChunk> {

	@Override
	public String parse(ItemChunk chunk) {
		return "* " + chunk.getContent().trim() + "\n";
	}

}
