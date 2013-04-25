package br.com.caelum.tubaina.parser.html.kindle;

import com.google.inject.Inject;

import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ImageTagTemplate;

public class ImageTag implements Tag<ImageChunk> {
	
	private ImageTagTemplate template;
	
	@Inject
	public ImageTag(Parser parser) {
		template = new ImageTagTemplate(parser);
	}
	
	@Override
	public String parse(ImageChunk chunk) {
		return template.parse(chunk.getPath(), chunk.getOptions(), true);
	}
	
}	
