package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ListTagTemplate;

public class ListTag implements Tag<ListChunk> {

	private ListTagTemplate template = new ListTagTemplate();
	
	@Override
	public String parse(ListChunk chunk) {
		return template.parse(chunk);
	}

}
