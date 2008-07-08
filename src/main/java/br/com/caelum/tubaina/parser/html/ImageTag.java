package br.com.caelum.tubaina.parser.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.parser.Tag;

public class ImageTag implements Tag {

	private static final String RELATIVEPATH = "../resources/";

	public String parse(final String path, final String options) {
		String output = "<div class=\"image\">";
		String imgsrc = FilenameUtils.getName(path);
		output = output + "<img src=\"" + RELATIVEPATH + imgsrc + "\"";

		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher dMatcher = description.matcher(options);

		// The image is resized when copied

		if (dMatcher.find()) {
			output = output + " alt=\"" + dMatcher.group(1) + "\" />";
			output = output + "<span class=\"image\">" + dMatcher.group(1) + "</span>";
		} else {
			output = output + " alt=\"" + imgsrc + "\" />";
		}

		output = output + "</div>";

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
