package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class ListTagTemplate implements Tag {

	public String parse(Chunk chunk) {
		if (options.contains("number"))
			return "<ol>" + content + "</ol>";
		if (options.contains("letter"))
			return "<ol class=\"letter\">" + content + "</ol>";
		if (options.contains("roman"))
			return "<ol class=\"roman\">" + content + "</ol>";
		return "<ul>" + content.trim() + "</ul>";

	}

}
