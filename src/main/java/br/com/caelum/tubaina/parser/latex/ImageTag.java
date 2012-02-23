package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.parser.Tag;

public class ImageTag implements Tag {

	double pageWidth = 175;

	public String parse(final String path, final String options) {
		String output = "\\begin{figure}[H]\n\\centering\n";

		output = output + "\\includegraphics";

		Pattern label = Pattern.compile("(?s)(?i)label=(\\w+)%?");
		Matcher labelMatcher = label.matcher(options);

		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher descriptionMatcher = description.matcher(options);
		
		Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
		Matcher horizontalMatcher = horizontalScale.matcher(options);

		Pattern actualWidth = Pattern.compile("(?s)(?i)\\[(.+?)\\]");
		Matcher actualWidthMatcher = actualWidth.matcher(options);

		double width = Double.MAX_VALUE;
		if (actualWidthMatcher.find()) {
			width = Double.parseDouble(actualWidthMatcher.group(1));
		}

		if (horizontalMatcher.find()) {
			output = output + "[width=" + pageWidth * (Double.parseDouble(horizontalMatcher.group(1)) / 100) + "mm]";
		} else if (width > pageWidth) {
			output = output + "[width=\\textwidth]";
		} else {
			output = output + "[scale=1]";
		}

		String imgsrc = FilenameUtils.getName(path);
		output = output + "{" + imgsrc + "}\n";
		
		if (labelMatcher.find()) {
			output += "\\label{" + labelMatcher.group(1) + "}\n";
		}

		if (descriptionMatcher.find()) {
			output = output + "\n\n\\caption{" + descriptionMatcher.group(1) + "}\n\n";
		}

		output = output + "\\end{figure}\n\n";

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
