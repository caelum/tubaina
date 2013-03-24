package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ImageTagTemplate;

public class ImageTag implements Tag<ImageChunk> {
	
	private ImageTagTemplate template;
	
	@Override
	public String parse(ImageChunk chunk) {
		return template.parse(chunk.getPath(), chunk.getOptions(), true);
	}
	
}	
