package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Tag;

public class EscapeTag implements Tag {

	public String parse(String content, String options) {
		int pos = -1;
		StringBuilder text = new StringBuilder();
		while (true) {
			int found = content.indexOf("[verbatim]", pos + 1);
			if (found == -1) {
				text.append(content.substring(pos + 1));
				break;
			}
			text.append(escape(content.substring(pos + 1, found)));
			int fim = content.indexOf("[/verbatim]", found);
			if (fim == -1) {
				throw new TubainaException("Verbatim end tag is missing");
			}
			text.append(content.substring(found + "[verbatim]".length(), fim));
			pos = fim + "[/verbatim]".length() -1;
		}
		return text.toString();
	}

	private String escape(String content) {
		for (Escape esc : Escape.values()) {
			content = esc.escape(content);
		}
		return content;
	}

}
