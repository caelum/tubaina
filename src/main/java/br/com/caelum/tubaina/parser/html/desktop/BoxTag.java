package br.com.caelum.tubaina.parser.html.desktop;

import com.google.inject.Inject;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class BoxTag implements Tag<BoxChunk> {

	private final HtmlSanitizer sanitizer;

	@Inject
	public BoxTag(HtmlSanitizer sanitizer) {
		this.sanitizer = sanitizer;
	}
	
	@Override
	public String parse(BoxChunk chunk) {
		String title = sanitizer.sanitize(chunk.getTitle().trim());
		return "<div class=\"box\"><h4>" + title 
				+ "</h4>\n" + chunk.getContent().trim() + "</div>";
	}

}
