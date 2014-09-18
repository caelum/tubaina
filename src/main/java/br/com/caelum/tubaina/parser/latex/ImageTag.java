package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import com.google.inject.Inject;

import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;

public class ImageTag implements Tag<ImageChunk> {

	private final Parser parser;

	@Inject
	public ImageTag(Parser parser) {
		this.parser = parser;
	}

	@Override
	public String parse(ImageChunk chunk) {
		String options = chunk.getOptions();
		double imageWidthInMilimeters = chunk.getWidth() * 25.4 / chunk.getDpi();
		
		StringBuilder output = new StringBuilder("\n\n\\begin{figure}[H]\n\\begin{center}\n");
		output.append("\\includegraphics");
		
		Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
		Matcher widthOptionMatcher = horizontalScale.matcher(options);
		if (widthOptionMatcher.find()) {
			output.append("[width=" + (TubainaBuilder.getMaximumWidth() * (Double.parseDouble(widthOptionMatcher.group(1)) / 100)) + "mm]");
		} else if (imageWidthInMilimeters > TubainaBuilder.getMaximumWidth()) {
			output.append("[width=\\textwidth]");
		} else {
			output.append("[scale=1]");
		}

		String imgsrc = FilenameUtils.getName(chunk.getPath());
		output.append("{" + imgsrc + "}\n");
		
		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher descriptionMatcher = description.matcher(options);
		if (descriptionMatcher.find()) {
			String caption = descriptionMatcher.group(1);
			caption = parser.parse(caption);
			output.append("\n\n\\caption{" + caption + "}\n\n");
		}
		else {
			output.append("\n\n\\captionsetup{labelsep=none}\\caption{}\n\n");
		}
		
		Pattern label = Pattern.compile("(?s)(?i)label=(\\S+)?");
		Matcher labelMatcher = label.matcher(options);
		if (labelMatcher.find()) {
			String givenLabel = labelMatcher.group(1);
			output.append("\\label{" + (givenLabel != null? givenLabel : imgsrc) + "}\n");
		}

		output.append("\\end{center}\\end{figure}\n\n");

		return output.toString();
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
