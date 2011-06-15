package br.com.caelum.tubaina.parser.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.parser.Tag;

public class ImageTag implements Tag {

	private static final String RELATIVEPATH = "$$RELATIVE$$/";

	public String parse(final String path, final String options) {
		String imgsrc = FilenameUtils.getName(path);
		String output = "<img src=\"" + RELATIVEPATH + imgsrc + "\"";

		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher descriptionMatcher = description.matcher(options);

		// The image is resized when copied

		if (descriptionMatcher.find()) {
			output = output + " alt=\"" + descriptionMatcher.group(1) + "\" />";
		} else {
			output = output + " alt=\"" + imgsrc + "\" />";
		}

		return output;
	}

	public Integer getScale(final String string) {
		if (string == null) {
			return null;
		}
		Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
		Matcher sMatcher = horizontalScale.matcher(string);

		if (sMatcher.find()) {
			return Integer.parseInt(sMatcher.group(1));
		}
		return null;
	}
}
