package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ItemTagTemplate;

public class ItemTag implements Tag {
	private ItemTagTemplate template = new ItemTagTemplate();
	
	public String parse(Chunk chunk) {
		return template.parse(chunk);
	}
}
