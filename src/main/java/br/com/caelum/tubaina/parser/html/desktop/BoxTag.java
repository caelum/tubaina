package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.Sanitizer;

import com.google.inject.Inject;

public class BoxTag implements Tag<BoxChunk> {

	private final Sanitizer sanitizer;
	private final Parser parser;

	@Inject
	public BoxTag(Sanitizer sanitizer, Parser parser) {
		this.sanitizer = sanitizer;
		this.parser = parser;
	}
	
	@Override
	public String parse(BoxChunk chunk) {
		String title = sanitizer.sanitize(chunk.getTitle().trim());
		return "<div class=\"box\"><h4>" + parser.parse(title) 
				+ "</h4>\n" + chunk.getContent().trim() + "</div>";
	}

}
