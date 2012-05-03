package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class BoxTagTemplate implements Tag {

	public String parse(String content, String title) {

		return "<div class=\"box\"><h4>" + title.trim() 
				+ "</h4>\n" + content.trim() + "</div>";
	}

}
