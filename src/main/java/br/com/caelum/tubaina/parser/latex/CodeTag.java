package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

	private final Indentator indentator;

	public static final String BEGIN = "{\\begin{flushright} \\begin{minipage}{16.9cm} \\small \n\\begin{minted}";
	public static final String END = "\n\\end{minted}\n\\end{minipage}\\end{flushright}}";

	public CodeTag(Indentator indentator) {
		this.indentator = indentator;
	}
	public String parse(String string, String options) {
		options = options == null ? "" : options;
		String lineNumbers = options.contains("#") ? "[linenos, numbersep=5pt]": "";
		options = options.replaceAll("#", "").trim();
		String chosenLanguage = options.trim().split(" ")[0].trim();
		
		if(chosenLanguage.isEmpty()){
			chosenLanguage = "text";
		}
		
		
		String indentedString = this.indentator.indent(string);
		return CodeTag.BEGIN + lineNumbers + "{" + chosenLanguage + "}\n" + indentedString + CodeTag.END;
	}
	
}
