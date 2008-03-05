package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class ListTag implements Tag {

	public String parse(String content, String options) {
		if (options.contains("number"))
			return "<ol class=\"list\" type=\"1\">" + content + "</ol>";
		if (options.contains("letter"))
			return "<ol class=\"list\" type=\"a\">" + content + "</ol>";
		if (options.contains("roman"))
			return "<ol class=\"list\" type=\"I\">" + content + "</ol>";
		return "<ul class=\"list\">" + content.trim() + "</ul>";

	}

}
