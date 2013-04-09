package br.com.caelum.tubaina.parser.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.parser.Parser;

public class ImageTagTemplate {
	
	private final Parser parser;

	public ImageTagTemplate(Parser parser) {
		this.parser = parser;
	}

	// TODO: make it work more gracefully... i.e., eliminate this workaround
	private static final String RELATIVEPATH = "$$RELATIVE$$/";

	public String parse(final String path, final String options, boolean shouldUseHTMLWidth) {
		String imgsrc = FilenameUtils.getName(path);
		String output = "<img src=\"" + RELATIVEPATH + imgsrc + "\" ";

		Pattern label = Pattern.compile("(?s)(?i)label=(\\S+)%?");
		Matcher labelMatcher = label.matcher(options);
		
		if (labelMatcher.find()) {
			output = output + "id=\"" + labelMatcher.group(1) + "\" ";
		} else {
		    output = output + "id=\"" + imgsrc + "\" ";
		}
		
		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher descriptionMatcher = description.matcher(options);
		
		if (shouldUseHTMLWidth && getScale(options) != null) {
		    output += "width='" + getScale(options) + "%' "; 
		}
		
		// The image is resized when copied
		if (descriptionMatcher.find()) {
			String subtitle = descriptionMatcher.group(1);
			output += "alt=\"" + subtitle + "\" />\n";
			subtitle = parser.parse(subtitle);
			output += "<div>"+ subtitle +"</div><br><br>";
		} else {
			output += "alt=\"" + imgsrc + "\" />";
		}
		
		return output;
	}
	
	public String parse(final String path, final String options) {
	    return parse(path, options, false);
	}

	public Integer getScale(final String options) {
		if (options == null) {
			return 100;
		}
		Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
		Matcher sMatcher = horizontalScale.matcher(options);

		if (sMatcher.find()) {
			return Integer.parseInt(sMatcher.group(1));
		}
		return 100;
	}
	
	public boolean shouldScale(final String options) {
	    if (options == null) {
	        return false;
	    }
        Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
        Matcher sMatcher = horizontalScale.matcher(options);
        
        return sMatcher.matches();
	}
}
