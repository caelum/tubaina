package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.parser.Tag;

public class ImageTag implements Tag {

	double pageWidth = 175;

	public String parse(final String path, final String options) {
		String output = "\\begin{center}\n\n";

		output = output + "\\includegraphics";

		Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
		Matcher sMatcher = horizontalScale.matcher(options);

		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher dMatcher = description.matcher(options);

		Pattern actualWidth = Pattern.compile("(?s)(?i)\\[(.+?)\\]");
		Matcher aMatcher = actualWidth.matcher(options);

		double width = Double.MAX_VALUE;
		if (aMatcher.find()) {
			width = Double.parseDouble(aMatcher.group(1));
		}

		if (sMatcher.find()) {
			output = output + "[width=" + pageWidth * (Double.parseDouble(sMatcher.group(1)) / 100) + "mm]";
		} else if (width > pageWidth) {
			output = output + "[width=\\textwidth]";
		} else {
			output = output + "[scale=1]";
		}

		String imgsrc = FilenameUtils.getName(path);
		output = output + "{" + imgsrc + "}\n";

		if (dMatcher.find()) {
			output = output + "\\newline\n\n\\small{Fig.: " + dMatcher.group(1) + "}\n\n";
		}

		output = output + "\\end{center}\n\n";

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
