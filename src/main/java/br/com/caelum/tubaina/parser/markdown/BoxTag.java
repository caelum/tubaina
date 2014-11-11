package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag<BoxChunk> {

	@Override
	public String parse(BoxChunk chunk) {
		String title = chunk.getTitle();
		return "\n\n### " + title + "\n\n";
	}
}
