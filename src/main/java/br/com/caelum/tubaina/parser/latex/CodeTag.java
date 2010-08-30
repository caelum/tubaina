package br.com.caelum.tubaina.parser.latex;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.CodeHighlightTag;
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
		string = escapeDollar(string);
//TODO:highlights
//		string = new CodeHighlightTag().parseLatex(string, highlights);
		return CodeTag.BEGIN + "{" + cleanOptions + "}\n" + string + CodeTag.END;
	}
	
	private String escapeDollar(String string) {
		return string.replaceAll("\\$", "\\$\\\\mathdollar\\$");
	}

	public String parseOld(String string, String options) {
		boolean properties = isProperties(options);
		List<Integer> highlights = new CodeHighlightTag().getHighlights(options);

		// indenting
		string = indentator.indent(string);
		
		string = string.replaceAll("\\\\(#|:| |=)", "º$1"); // º is now the
															// escape char
		string = new EscapeTag().parse(string, null);
		string = parseSpaces(string);
		if (properties) {
			string = Escape.SHARP.unescape(string);
			string = parseValue(string);
			string = parseComments(string);
			string = Escape.SHARP.escape(string);
		}
		string = escapeFixes(string);
		string = parseSymbols(string);
		string = new CodeHighlightTag().parseLatex(string, highlights);
		return BEGIN_OLD + string + END_OLD;
	}

	private String parseSymbols(String string) {
		String output = string.replaceAll("(-|<|>|=|\"|\'|/|@)", "\\\\verb#$1#");
		return output;
	}

	private String escapeFixes(String string) {
		String backslashEscaped = Escape.BACKSLASH.escape("\\");
		string = string.replaceAll("º", Matcher.quoteReplacement(backslashEscaped));// \ is
																					// the
																					// escape
																					// char
																					// again
		string = Escape.HYPHEN.unescape(string);
		string = Escape.SHIFT_LEFT.unescape(string);
		string = Escape.SHIFT_RIGHT.unescape(string);
		string = string.replaceAll("__", "\\\\_\\\\_");
		string = string.replaceAll("%%", "\\\\%\\\\%");
		return string;
	}

	private boolean isProperties(String options) {
		if (options == null)
			return false;
		return options.contains("properties");
	}

	private String parseSpaces(String string) {
		string = string.replaceAll("\n", "\\\\\\\\\n");
		string = string.replaceAll("\t", "~~~~");
		string = string.replaceAll(" ", "~");
		return string.replaceAll("(?m)^", "\\\\black ");
	}

	private String parseComments(String string) {
		return matchPattern(string, "(?i)(^|[^º])(#)", "texcomment");
	}

	private String parseValue(String string) {
		return matchPattern(string, "(?i)(^[^#]*?[^º#](?:~|=|:))(.+)", "texvalue");
	}

	private String matchPattern(String string, String regex, String tag) {
		Pattern pattern = Pattern.compile(regex);

		String[] lines = string.split("\n");
		String output = "";
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				output += line.substring(0, matcher.start(2));
				output += "\\" + tag + " ";
				output += line.substring(matcher.start(2));
				output += "\n";
			} else {
				output += line + "\n";
			}
		}
		output = output.substring(0, Math.max(output.length() - 1, 0));
		return output;
	}
}
