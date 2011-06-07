package br.com.caelum.tubaina.parser.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

	public static final String START_HEADER = "<pre";
	public static final String CLOSE_HEADER = ">\n";
	public static final String END = "\n</pre>";

	public String parse(String content, String options) {
		String preOptions = classAssembler(options);

		return START_HEADER + preOptions + CLOSE_HEADER + content + END;
	}
	
	private String classAssembler(String options) {
		String language = detectLanguage(options);
		String numbered = options.contains("#") ? " numbered" : "";
		String highlights = detectHighlights(options);

		return " class=\"code" + language + numbered + "\"" + highlights;
	}
	
	private String detectLanguage(String options) {
		if (options != null) {
			String languageCandidate = options.split(" ")[0];
			if (!languageCandidate.contains("#") && !languageCandidate.startsWith("h=") && !languageCandidate.isEmpty())
				return " " + languageCandidate;
		}
		return " text";
	}
	
	private String detectHighlights(String options) {
		Pattern pattern = Pattern.compile("h=([\\d+,]+)");
		Matcher matcher = pattern.matcher(options);
		if (matcher.find()) {
			return " data-highlight=\"" + matcher.group(1) + "\"";
		}
		return "";
	}
	
}
