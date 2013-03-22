package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag<BoxChunk> {

	@Override
	public String parse(BoxChunk chunk) {

		return "<div class=\"box\"><h4>" + chunk.getTitle().trim() 
				+ "</h4>\n" + chunk.getContent().trim() + "</div>";
	}

}
