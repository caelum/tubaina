package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ItemTagTemplate implements Tag<ItemChunk> {

	@Override
	public String parse(ItemChunk chunk) {
		return "<li>" + chunk.getContent() + "</li>";
	}
}
