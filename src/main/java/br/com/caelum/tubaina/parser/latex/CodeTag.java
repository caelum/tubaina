package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

	private final Indentator indentator;

	public static final String BEGIN = "\\small \n\\begin{minted}";
	public static final String END = "\n\\end{minted}\\normalsize";

	public CodeTag(Indentator indentator) {
		this.indentator = indentator;
	}
	public String parse(String string, String options) {
		StringBuilder optionsBuilder = new StringBuilder("[");
		if (options.contains("#")) {
			optionsBuilder.append("linenos, numbersep=5pt");
			options = removeSharp(options); 
		}
		Matcher highlightPattern = Pattern.compile("h=(\\d+(,\\d+)*)").matcher(options);
		if (highlightPattern.find()) {
			if(optionsBuilder.length() > 1)
				optionsBuilder.append(", ");
			
			String highlights = highlightPattern.group();
			optionsBuilder.append(highlights);
			options = removeHighlights(options, highlights); 
		}
		optionsBuilder.append("]");
		
		String optionalParameters = optionsBuilder.length() > 2 ? optionsBuilder.toString() : "";
		String chosenLanguage = options == null ? "" : options.trim().split(" ")[0].trim();	
		if(chosenLanguage.isEmpty()){
			chosenLanguage = "text";
		}
		
		String indentedString = this.indentator.indent(string);
		return CodeTag.BEGIN + optionalParameters + "{" + chosenLanguage + "}\n" + indentedString + CodeTag.END;
	}


	private String removeHighlights(String options, String highlights) {
		return options.replace(highlights, "");
	}

	private String removeSharp(String options) {
		int lastSharp = options.lastIndexOf("#");
		return options.substring(0, lastSharp) + options.substring(lastSharp + 1);
	}
	
}
