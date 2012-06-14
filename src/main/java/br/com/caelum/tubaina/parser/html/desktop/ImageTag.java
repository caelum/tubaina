package br.com.caelum.tubaina.parser.html.desktop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.ImageTagTemplate;

public class ImageTag implements Tag {

	private ImageTagTemplate template = new ImageTagTemplate();
	
	public String parse(final String path, final String options) {
	    Pattern label = Pattern.compile("(?s)(?i)label=(\\w+)?");
        Matcher labelMatcher = label.matcher(options);
        if (labelMatcher.matches()) {
            throw new TubainaException("Image labels are not yet supported for html output");
        }
	    
		return template.parse(path, options, false);
	}

	public Integer getScale(final String string) {
		return template.getScale(string);
	}
}
