package br.com.caelum.tubaina.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CodeHighlightTag {

	private static final Logger LOG = Logger.getLogger(CodeHighlightTag.class);


	public String parseHtml(String text, List<Integer> highlights) {
		return parseHtml(text, highlights, 1);
	}
	public String parseHtml(String text, List<Integer> highlights, int start) {
		return parse(text, highlights, start, "<strong>", "</strong>");
	}

	public String parseLatex(String text, List<Integer> highlights) {
		return parseLatex(text, highlights, 1);
	}
	public String parseLatex(String text, List<Integer> highlights, int start) {
		return parse(text, highlights, start, "{\\bf ", "}");
	}
	public String parse(String text, List<Integer> highlights, int start, String openTag, String closeTag) {
		if (highlights == null) {
			return text;
		}
		String[] strings = text.split("\n");
		for (int i : highlights) {
			try {
				strings[i + start - 2] = openTag + strings[i + start - 2] + closeTag;
			} catch (ArrayIndexOutOfBoundsException e) {
				LOG.warn("Invalid line number: " + i);
			}
		}
		String output = "";
		for (String string : strings) {
			output += string + "\n";
		}
		return output.substring(0, Math.max(output.length() -1, 0));
	}
	
	public List<Integer> getHighlights(String options) {
		Pattern pattern = Pattern.compile("h=([\\d+,]+)");
		Matcher matcher = pattern.matcher(options);
		List<Integer> ilines = new ArrayList<Integer>();
		if (matcher.find()) {
			String[] lines = matcher.group(1).split(",");
			for (String string : lines) {
				ilines.add(Integer.parseInt(string));
			}
			
		}
		return ilines;
	}
}
