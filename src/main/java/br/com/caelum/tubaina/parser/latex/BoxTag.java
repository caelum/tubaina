package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;

import com.google.inject.Inject;

public class BoxTag implements Tag<BoxChunk> {

	private final Parser parser;

	@Inject
	public BoxTag(Parser parser) {
		this.parser = parser;
	}
	
	@Override
	public String parse(BoxChunk chunk) {
		String title = chunk.getTitle();
		String sanitizedTitle = title.isEmpty() ? "" : "{" + parser.parse(title) + "}";
		return "\\begin{tubainabox}" + sanitizedTitle + "\n" + chunk.getContent() + "\n\\end{tubainabox}";
	}
}
