package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ImageTagTemplate;

import com.google.inject.Inject;

public class ImageTag implements Tag<ImageChunk> {
	
	private ImageTagTemplate template;
	
	@Inject
	public ImageTag(Parser parser) {
		template = new ImageTagTemplate(parser);
	}
	
	@Override
	public String parse(ImageChunk chunk) {
		return template.parse(chunk, true);
	}
	
}	
