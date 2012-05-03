package br.com.caelum.tubaina.parser.html.desktop;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.CodeHighlightTag;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class XmlTag implements Tag {

	public static final String BEGIN = "<div class=\"xml\"><code class=\"xml\">";
	public static final String END = "</code></div>";
	
	private final Indentator indentator;

	public XmlTag(Indentator indentator) {
		this.indentator = indentator;
		
	}
	public String parse(String string, String options) {
		List<Integer> highlights = new CodeHighlightTag().getHighlights(options);
		
		string = indentator.indent(string);
		
		string = parseSpaces(string);
		string = parseNormal(string);
		string = parseComments(string);
		string = parseSpecials(string);
		string = parseTags(string);
		string = new CodeHighlightTag().parseHtml(string, highlights);
		string = BEGIN + string + END;
		return string;
	}

	private String parseSpaces(String string) {
		string = string.replaceAll("<", "&lt;");
		string = string.replaceAll(">", "&gt;");
		string = string.replaceAll(" ", "&nbsp;");
		string = string.replaceAll("\n", "<br />\n");
		string = string.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		return string;
	}

	private String parseNormal(String string) {
		return matchPattern(string, "(?s)(?i)&gt;(.+?)&lt;", "texnormal");
	}

	private String parseTags(String string) {
		string = string.replaceAll("&lt;/(.+?)&gt;", "<span class=\"textag\">$0</span>");
		Pattern pattern = Pattern.compile("(?s)(?i)(&lt;\\w.*?)(&nbsp;(?:.*?))?(/?&gt;)");
		Matcher matcher = pattern.matcher(string);
		String output = "";
		int lastend = 0;
		while (matcher.find()) {
			output += string.substring(lastend, matcher.start(1));
			output += "<span class=\"textag\">";
			output += matcher.group(1);
			if (matcher.group(2) != null) {
				output += "</span>";
				output += parseAttribs(matcher.group(2));
				output += "<span class=\"textag\">";
			}
			output += matcher.group(3);
			output += "</span>";
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
			output += "<span class=\"texattrib\">";
			output += matcher.group(1) + "=";
			output += "</span>";
			output += "<span class=\"texvalue\">\"" + matcher.group(2) + "\"</span>";
			lastend = matcher.end();
		}
		output += string.substring(lastend);
		return output;
	}

	private String parseSpecials(String string) {
		return matchPattern(string, "(?s)(?i)(&lt;(\\?|!DOCTYPE)(.+?)&gt;)", "texspecial");
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
		String openSpan = "<span class=\"" + tag + "\">";
		String output = openSpan;
		output += matcher.group(1).replaceAll("\n", "</span>\n"+openSpan);
		output += "</span>";
		return output;
	}

	private String parseComments(String string) {
		String output = matchPattern(string, "(?s)(?i)(&lt;!--(.*?)--&gt;)", "texcomment");
		return output;
	}

}
