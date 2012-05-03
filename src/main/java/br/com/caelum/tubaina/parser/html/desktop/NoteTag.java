package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.NoteTagTemplate;

public class NoteTag implements Tag {

	private NoteTagTemplate template = new NoteTagTemplate();
	
	public String parse(String content, String options) {
		return template.parse(content, options);
	}

}
