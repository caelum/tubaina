package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

	private final Indentator indentator;

	public static final String BEGIN = "{\\small\n\\begin{minted}[mathescape]";
	public static final String END = "\n\\end{minted}\n}";
	
	public static final String BEGIN_OLD = "{\\vspace{1em}{\n" + "\\small \\noindent \\ttfamily \n";
	public static final String END_OLD = "\n}}\n\\newline\n";

	public CodeTag(Indentator indentator) {
		this.indentator = indentator;
	}

	public String parse(String string, String options) {
		String cleanOptions = options == null ? "" : options.trim().split(" ")[0].trim();	
		if(cleanOptions.isEmpty())
			cleanOptions = "text";
		String indentedString = this.indentator.indent(string);
		return CodeTag.BEGIN + "{" + cleanOptions + "}\n" + indentedString + CodeTag.END;
	}
	

	
}
