package br.com.caelum.tubaina.parser.html.desktop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ImageTagTemplate;

public class ImageTag implements Tag<ImageChunk> {

	private ImageTagTemplate template = new ImageTagTemplate();
	
	@Override
	public String parse(ImageChunk chunk) {
	    Pattern label = Pattern.compile("(?s)(?i)label=(\\w+)?");
        Matcher labelMatcher = label.matcher(chunk.getOptions());
        if (labelMatcher.matches()) {
            throw new TubainaException("Image labels are not yet supported for html output");
        }
	    
		return template.parse(chunk.getPath(), chunk.getOptions(), false);
	}

	public Integer getScale(final String string) {
		return template.getScale(string);
	}
	
	public boolean shouldResize(final String options) {
	    return template.shouldScale(options);
	}
}
