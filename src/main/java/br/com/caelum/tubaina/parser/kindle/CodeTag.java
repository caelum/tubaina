package br.com.caelum.tubaina.parser.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

	public String parse(String content, String options) {
		return "<code class=\"" + detectLanguage(options) + "\">\n"+ content + "\n</code>";
	}
	
	private String detectLanguage(String options) {
		if (options != null) {
			String languageCandidate = options.trim().split(" ")[0];
			if (!languageCandidate.contains("#") && !languageCandidate.startsWith("h=") && !languageCandidate.isEmpty())
				return languageCandidate;
		}
		return "text";
	}
	
}
