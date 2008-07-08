package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class EscapeTag implements Tag{

	public String parse(String string, String options) {
		
		for (Escape esc : Escape.values()) {
			string = esc.escape(string);
		}
		return string;
	}

}
