package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ListTag implements Tag<ListChunk> {

	@Override
	public String parse(ListChunk chunk) {
		String listHeader = "\\begin{enumerate}[";
		String options = chunk.getType();
		if (options.contains("number"))
			listHeader += "1)";
		else if (options.contains("letter"))
			listHeader += "a)";
		else if (options.contains("roman"))
			listHeader += "I)";
		else {
			//If type is invalid, we use itemize environment
			return "\\begin{itemize}" + chunk.getContent() + "\\end{itemize}";
		}
		
		return listHeader + "]\n" + chunk.getContent() + "\n\\end{enumerate}";
	}

}
