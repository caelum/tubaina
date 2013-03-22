package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class ListTag implements Tag {

	@Override
	public String parse(Chunk chunk) {
		String listHeader = "\n\n\\begin{enumerate}[";
		if (options.contains("number"))
			listHeader += "1)";
		else if (options.contains("letter"))
			listHeader += "a)";
		else if (options.contains("roman"))
			listHeader += "I)";
		else {
			//If type is invalid, we use itemize environment
			return "\n\n\\begin{itemize}" + content + "\\end{itemize}";
		}
		
		return listHeader + "]\n" + content + "\n\\end{enumerate}";
	}

}
