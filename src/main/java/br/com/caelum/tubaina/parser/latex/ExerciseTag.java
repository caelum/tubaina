package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class ExerciseTag implements Tag {

	public String parse(String string, String options) {
		
		return "\\label{ex:"+options+"}\n\\begin{enumerate}[1)]\n" + string + "\n\\end{enumerate}";
	}

}
