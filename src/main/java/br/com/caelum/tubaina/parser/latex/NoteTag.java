package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class NoteTag implements Tag {

	public String parse(String string, String title) {
		// The first \n is the title/content sepparator
		
		return "\\begin{tubainabox}{" + ((title == null)?"Instructor's note":title)  + "}\n" +
					string + "\n" +
				"\\end{tubainabox}";
	}
}
