package br.com.caelum.tubaina.parser.html;

import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag {

	public String parse(String content, String title) {

		return "<div class=\"box\"><h3>" + title.trim() 
				+ "</h3>\n" + content.trim() + "</div>";
	}

}
