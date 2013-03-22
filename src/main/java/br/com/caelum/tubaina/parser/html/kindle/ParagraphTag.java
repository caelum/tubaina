package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ParagraphTagTemplate;

public class ParagraphTag implements Tag {

	private ParagraphTagTemplate template = new ParagraphTagTemplate();
	
	public String parse(Chunk chunk) {
		return template.parse(chunk);
	}

}
