package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag {

	public String parse(String content, String title) {
		return "<hr/><b>" + title.trim() 
				+ "</b>\n" + content.trim() + "<hr/>";
	}

}
