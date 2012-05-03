package br.com.caelum.tubaina.parser.html.desktop;

import java.util.List;

import br.com.caelum.tubaina.parser.CodeHighlightTag;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;
import de.java2html.Java2Html;
import de.java2html.JavaSourceConversionSettings;
import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSource2Xhtml11Converter;
import de.java2html.options.JavaSourceConversionOptions;

public class JavaTag implements Tag {

	private final Indentator indentator;

	public JavaTag(Indentator indentator) {
		this.indentator = indentator;
	}
	public String parse(String string, String options) {

		boolean numbered = isNumbered(options);
		
		List<Integer> highlights = new CodeHighlightTag().getHighlights(options);
		
		string = indentator.indent(string);
	
		JavaSourceConversionOptions conversionOptions = JavaSourceConversionOptions.getDefault();
		conversionOptions.setShowLineNumbers(numbered);
		
		JavaSourceConversionSettings settings = new JavaSourceConversionSettings(conversionOptions) {
			@Override
			public IJavaSourceConverter createConverter() {
				return new JavaSource2Xhtml11Converter();
			}
		};
		string = Java2Html.convertToHtml(string, settings);
		/* Removing Java2Html comments (we added it on each Sections page) */
		string = string.replaceAll("<!--(.+?)-->\n", "");
		string = string.replaceFirst("<div class=\"java\"><code class=\"java\">","$0\n"); 
		string = string.replaceAll("&#xA0;", "&nbsp;");
		 
		string = new CodeHighlightTag().parseHtml(string.trim(), highlights, 2);
		return string;
	}
	private boolean isNumbered(String options) {
		return options.contains("#");
	}

}
