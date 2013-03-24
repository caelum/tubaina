package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ItemTagTemplate;

public class ItemTag implements Tag<ItemChunk> {
	private ItemTagTemplate template = new ItemTagTemplate();
	
	@Override
	public String parse(ItemChunk chunk) {
		return template.parse(chunk);
	}
}
