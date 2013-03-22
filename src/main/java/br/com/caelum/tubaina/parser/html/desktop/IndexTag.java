package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.IndexTagTemplate;

public class IndexTag implements Tag {
	private IndexTagTemplate template = new IndexTagTemplate();
	
	public String parse(Chunk chunk) {
		return template.parse(chunk);
	}

}
