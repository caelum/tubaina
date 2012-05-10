package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ImageTagTemplate;

public class ImageTag implements Tag {

	private ImageTagTemplate template = new ImageTagTemplate();
	
	public String parse(final String path, final String options) {
		return template.parse(path, options, false);
	}

	public Integer getScale(final String string) {
		return template.getScale(string);
	}
}
