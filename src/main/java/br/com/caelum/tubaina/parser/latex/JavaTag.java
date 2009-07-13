package br.com.caelum.tubaina.parser.latex;

import java.util.List;

import br.com.caelum.tubaina.parser.CodeHighlightTag;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;
import de.java2html.Java2Html;
import de.java2html.JavaSourceConversionSettings;
import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSource2TeXConverter;
import de.java2html.options.JavaSourceConversionOptions;

public class JavaTag implements Tag {


	private final Indentator indentator;

	public JavaTag(Indentator indentator) {
		this.indentator = indentator;
		
	}
	public String parse(String string, String opts) {
		boolean numbered = isNumbered(opts);
		List<Integer> highlights = new CodeHighlightTag().getHighlights(opts);
		
		string = indentator.indent(string);
		
		JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
			options.setShowLineNumbers(numbered);
			
		JavaSourceConversionSettings settings = new JavaSourceConversionSettings(options) {
			@Override
			public IJavaSourceConverter createConverter() {
				return new JavaSource2TeXConverter();
			}
		};
		string = Java2Html.convertToHtml(string, settings);
		string = new CodeHighlightTag().parseLatex(string.trim(), highlights, 7);
		string = escapeDecrement(string);
		return "{\\vspace{1em}\n\\small\n"+string+"}";
	}

	private String escapeDecrement(String string) {
		return Escape.HYPHEN.escape(string);
	}
	
	private boolean isNumbered(String opts) {
		return opts.contains("#");
	}

}
