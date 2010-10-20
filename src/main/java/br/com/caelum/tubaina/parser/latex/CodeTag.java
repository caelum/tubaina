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
		String chosenLanguage = options == null ? "" : options.trim().split(" ")[0].trim();	
		if(chosenLanguage.isEmpty()){
			chosenLanguage = "text";
		}
		String lineNumbers = options.contains("#") ? "[linenos]": "";
		
		String indentedString = this.indentator.indent(string);
		return CodeTag.BEGIN + lineNumbers + "{" + chosenLanguage + "}\n" + indentedString + CodeTag.END;
	}
	

	
}
