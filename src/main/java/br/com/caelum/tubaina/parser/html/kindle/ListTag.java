package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.KindleListTagTemplate;

public class ListTag implements Tag<ListChunk> {

	private KindleListTagTemplate template = new KindleListTagTemplate();
	
	@Override
	public String parse(ListChunk chunk) {
		return template.parse(chunk);
	}

}
