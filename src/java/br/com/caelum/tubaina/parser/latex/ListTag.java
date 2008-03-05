package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class ListTag implements Tag {

	public String parse(String content, String options) {
		String listHeader = "\\begin{enumerate}[";
		if (options.contains("number"))
			listHeader += "1)";
		else if (options.contains("letter"))
			listHeader += "a)";
		else if (options.contains("roman"))
			listHeader += "I)";
		else {
			//If type is invalid, we use itemize environment
			return "\\begin{itemize}" + content + "\\end{itemize}";
		}
		
		return listHeader + "]\n" + content + "\n\\end{enumerate}";
	}

}
