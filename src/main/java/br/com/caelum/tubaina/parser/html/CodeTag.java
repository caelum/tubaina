package br.com.caelum.tubaina.parser.html;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.CodeHighlightTag;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

	public static final String BEGIN = "<div class=\"java\"><code class=\"java\">";
	public static final String END = "</code></div>";

	private final Indentator indentator;

	public CodeTag(Indentator indentator) {
		this.indentator = indentator;
	}
	public String parse(String string, String options) {
		
		boolean properties = isProperties(options);
		List<Integer> highlights = new CodeHighlightTag().getHighlights(options);
		
		//indenting
		string = indentator.indent(string);
		
		string = parseSpaces(string);
		if (properties) {
			string = parseValue(string);
			string = parseComments(string);	
		}
		string = new CodeHighlightTag().parseHtml(string, highlights);
		return BEGIN + string + END;
	}
	
	private boolean isProperties(String options) {
		return options != null && options.contains("properties");
	}
	
	private String parseSpaces(String string) {
		string = string.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		string = string.replaceAll(" ", "&nbsp;");
		string = string.replaceAll("\n", "<br />\n");
		return string;
	}
	
	private String parseComments(String string) {
		return matchPattern(string, "(?i)(^|[^\\\\])(#)", "texcomment");
	}
	private String parseValue(String string) {
		return matchPattern(string, "(?i)(^[^#]*?[^\\\\#]&nbsp;|=|:)(.+)", "texvalue");
	}
	private String matchPattern(String string, String regex, String tag) {
		Pattern pattern = Pattern.compile(regex);
		
		String[] lines = string.split("\n");
		String output = "";
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				output += line.substring(0, matcher.start(2));
				output += "<span class=\"" + tag + "\">";
				output += line.substring(matcher.start(2));
				output += "</span>\n";
			} else {
				output += line + "\n";
			}
		}
		output = output.substring(0, Math.max(output.length() - 1,0));
		return output;
	}

}
