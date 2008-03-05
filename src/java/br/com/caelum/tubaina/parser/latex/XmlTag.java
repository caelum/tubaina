package br.com.caelum.tubaina.parser.latex;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.parser.CodeHighlightTag;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class XmlTag implements Tag {

	public static final Logger LOG = Logger.getLogger(XmlTag.class);
	
	public static final String BEGIN = "{\n" + "\\small \\noindent \\ttfamily \n";
	public static final String END = "\n}\n";

	private final Indentator indentator;
	
	public XmlTag(Indentator indentator) {
		this.indentator = indentator;
	}
	public String parse(String string, String options) {
		List<Integer> highlights = new CodeHighlightTag().getHighlights(options);
				
		string = indentator.indent(string);
		
		string = escape(string);
		string = parseSpaces(string);
		string = parseComments(string);
		string = parseSpecials(string);
		string = parseTags(string);
		string = parseNormal(string);
		string = parseSymbols(string);
		string = new CodeHighlightTag().parseLatex(string, highlights);
		string = BEGIN +string + END;
		return string;
	}

	private String escape(String string) {
		string =  new EscapeTag().parse(string, null);
		string = Escape.HYPHEN.unescape(string);
		string = Escape.SHIFT_LEFT.unescape(string);
		string = Escape.SHIFT_RIGHT.unescape(string);
		string = string.replaceAll("%%", "\\\\%\\\\%");
		return string;
	}
	private String parseSymbols(String string) {
		String output = string.replaceAll("(-|<|>|=|\"|\'|/|@)", "\\\\verb#$1#");
		return output;
	}
	private String parseSpaces(String string) {
		string = string.replaceAll("\n", "\\\\\\\\\n");
		string = string.replaceAll("\t", "~~~~");
		return string.replaceAll(" ", "~");
	}

	private String parseNormal(String string) {
		string = matchPattern(string, "(?s)(?i)>(.+?)<", "\\texnormal ");
		return string.replaceAll("(?m)^", "\\\\texnormal ");
	}

	private String parseTags(String string) {
		string = string.replaceAll("</(.+?)>", "\\\\textag $0");
		Pattern pattern = Pattern.compile("(?s)(?i)(<\\w.*?)(~(?:.*?))?(/?>)");
		Matcher matcher = pattern.matcher(string);
		String output = "";
		int lastend = 0;
		while (matcher.find()) {
			output += string.substring(lastend, matcher.start(1));
			output += "\\textag ";
			output += matcher.group(1);
			if (matcher.group(2) != null) {
				output += parseAttribs(matcher.group(2));
				output += "\\textag ";
			}
			output += matcher.group(3);
			lastend = matcher.end(3);
		}
		output += string.substring(lastend);
		return output;
	}

	private String parseAttribs(String string) {
		Pattern pattern = Pattern.compile("(?s)(?i)(\\w+)=\"(.*?)\"");
		Matcher matcher = pattern.matcher(string);
		int lastend = 0;
		String output = "";
		while (matcher.find()) {
			output += string.substring(lastend, matcher.start());
			output += "\\texattrib ";
			output += matcher.group(1) + "=";
			output += "\\texvalue \"" + matcher.group(2) + "\"";
			lastend = matcher.end();
		}
		output += string.substring(lastend);
		return output;
	}

	private String parseSpecials(String string) {
		return matchPattern(string, "(?s)(?i)(<(\\?|!DOCTYPE)(.+?)>)", "\\texspecial ");
	}

	private String matchPattern(String string, String regex, String tag) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		int lastend = 0;
		String output = "";
		while (matcher.find()) {
			output += string.substring(lastend, matcher.start(1));
			output += colorize(matcher, tag);
			lastend = matcher.end(1);
		}
		output += string.substring(lastend);
		return output;
	}

	private String colorize(Matcher matcher, String tag) {
		String output = tag;
		output += matcher.group(1).replaceAll("\n", "\n\\" + tag);
		return output;
	}

	private String parseComments(String string) {
		String output = matchPattern(string, "(?s)(?i)(<!--(.*?)-->)", "\\texcomment ");
		return output;
	}

}
