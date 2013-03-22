package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;

public class ImageTag implements Tag {
	
	private final Parser parser;

	@Override
	public String parse(Chunk chunk) {
		String output = "\n\n\\begin{figure}[H]\n\\begin{center}\n";

		output = output + "\\includegraphics";

		Pattern label = Pattern.compile("(?s)(?i)label=(\\S+)?");
		Matcher labelMatcher = label.matcher(options);

		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher descriptionMatcher = description.matcher(options);
		
		Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
		Matcher horizontalMatcher = horizontalScale.matcher(options);

		Pattern actualWidth = Pattern.compile("(?s)(?i)\\[(.+?),(.+?)\\]");
		Matcher actualWidthMatcher = actualWidth.matcher(options);

		double widthInPixels = Double.MAX_VALUE;
		int dpi = 72;
		if (actualWidthMatcher.find()) {
			widthInPixels = Double.parseDouble(actualWidthMatcher.group(1));
			dpi = Integer.parseInt(actualWidthMatcher.group(2));
		}

		double widthInMilimeters = widthInPixels * 25.4 / dpi;
		
		if (horizontalMatcher.find()) {
			output = output + "[width=" + TubainaBuilder.getMaximumWidth() * (Double.parseDouble(horizontalMatcher.group(1)) / 100) + "mm]";
		} else if (widthInMilimeters > TubainaBuilder.getMaximumWidth()) {
			output = output + "[width=\\textwidth]";
		} else {
			output = output + "[scale=1]";
		}

		String imgsrc = FilenameUtils.getName(path);
		output = output + "{" + imgsrc + "}\n";
		
		if (descriptionMatcher.find()) {
			String caption = descriptionMatcher.group(1);
			caption = parser.parse(caption);
			output = output + "\n\n\\caption{" + caption + "}\n\n";
		}
		
		if (labelMatcher.find()) {
			String givenLabel = labelMatcher.group(1);
			output += "\\label{" + (givenLabel != null? givenLabel : imgsrc) + "}\n";
		}

		output = output + "\\end{center}\\end{figure}\n\n";

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
