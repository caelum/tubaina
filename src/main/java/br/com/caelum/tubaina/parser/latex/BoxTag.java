package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag<BoxChunk> {

	@Override
	public String parse(BoxChunk chunk) {
		String title = chunk.getTitle();
		String sanitizedTitle = (title != null && !title.isEmpty()) ? title.trim() : "\\ ";
		return "\\begin{tubainabox}{" + sanitizedTitle + "}\n" + chunk.getContent() + "\n\\end{tubainabox}";
	}
}
