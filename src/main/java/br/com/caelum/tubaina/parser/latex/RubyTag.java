package br.com.caelum.tubaina.parser.latex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.CodeHighlightTag;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class RubyTag implements Tag {

	private final String BEGIN = "{\n" + "\\small \\noindent \\ttfamily \n";
	private final String END = "\n}\n";
	private final Indentator indentator;
	private final String identifier = "[\\p{Alnum}_]+";
	private final Map<Pattern, String> elementPatterns;
	private String output;

	public RubyTag(Indentator indentator) {
		this.indentator = indentator;
		this.elementPatterns = new HashMap<Pattern, String>();
		this.elementPatterns.put(Pattern.compile("(?m)^\\\\#.*$"), "comment");
		this.elementPatterns.put(Pattern.compile("(?s)^=begin.*?=end"), "comment");
		this.elementPatterns.put(Pattern.compile("(?s)^\".*?\""), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^'.*?'"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%r([^\\p{Alnum}\\[{(]).*?\\1[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%r\\(.*?\\)[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%r\\[.*?\\][eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%r\\{.*?\\}[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^/.*?/[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%s([^\\p{Alnum}\\[{(]).*?\\1"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%s\\(.*?\\)"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%s\\[.*?\\]"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%s\\{.*?\\}"), "symbol");
		this.elementPatterns.put(Pattern.compile("^:" + identifier), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[qQw])?([^\\p{Alnum}\\[{(]).*?\\2"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[qQw])?\\(.*?\\)"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[qQw])?\\[.*?\\]"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[qQw])?\\{.*?\\}"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^<<-?([`\"]?)(" + identifier + ")\\1.*?\\2"), "string");
		this.elementPatterns.put(Pattern.compile(
				"^((BEGIN)|(class)|(ensure)|(nil)|(self)|(when)|(END)|(defined[?]?)|(def)|" +
				"(false)|(not)|(super)|(while)|(alias)|(defined)|(for)|(or)|(then)|(yield)|" +
				"(and)|(do)|(if)|(redo)|(true)|(begin)|(else)|(in)|(rescue)|(undef)|(break)|" +
				"(elsif)|(module)|(retry)|(unless)|(case)|(end)|(next)|(return)|(until)|(raise))"), "keyword");
		this.elementPatterns.put(Pattern.compile("^((@@)|(@)|(\\\\\\$))" + identifier), "variable");
		this.elementPatterns.put(Pattern.compile("^[A-Z][\\p{Alnum}_]*"), "constant");
		this.elementPatterns.put(Pattern.compile("^[-+]?[0-9]+([.][0-9]+)?([eE][+-]?[0-9]+)?\\b"), "number");
		this.elementPatterns.put(Pattern.compile("^0[xX][A-Fa-f0-9]+\\b"), "number");
		this.elementPatterns.put(Pattern.compile("^0[0-7]+\\b"), "number");
		this.elementPatterns.put(Pattern.compile("^0[bB][01]+\\b"), "number");
	}

	public String parse(String code, String options) {
		CodeHighlightTag codeHighlightTag = new CodeHighlightTag();
		List<Integer> highlights = codeHighlightTag.getHighlights(options);
		String toProcess = this.indentator.indent(code);
		toProcess = escape(toProcess);
		toProcess = parseSpaces(toProcess);
		this.output = "";
		while (!toProcess.isEmpty()) {
			toProcess = processNextElement(toProcess);
		}
		this.output = parseSymbols(this.output);
		this.output = codeHighlightTag.parseLatex(this.output, highlights);
		return BEGIN + output + END;
	}
	
	private String escape(String string) {
		string = new EscapeTag().parse(string, null);
		string = Escape.HYPHEN.unescape(string);
		string = Escape.SHIFT_LEFT.unescape(string);
		string = Escape.SHIFT_RIGHT.unescape(string);
		return string;
	}
	
	private String parseSymbols(String string) {
		return string.replaceAll("(-|<|>|=|\"|\'|/|@)", "\\\\verb#$1#");
	}
	
	private String parseSpaces(String string) {
		string = string.replaceAll("\n", "\\\\\\\\\n");
		string = string.replaceAll("\t", "~~~~");
		return string.replaceAll(" ", "~");
	}

	private String processNextElement(String toProcess) {
		Pattern spacePattern = Pattern.compile("^(\\s|,|~|(\\\\\\\\))+");
		Matcher spaceMatcher = spacePattern.matcher(toProcess);
		if (spaceMatcher.find()) {
			this.output += spaceMatcher.group();
			return toProcess.substring(spaceMatcher.end());
		}
		for (Pattern elementPattern : this.elementPatterns.keySet()) {
			Matcher matcher = elementPattern.matcher(toProcess);
			if (matcher.find()) {
				if (this.elementPatterns.get(elementPattern).equals("constant"))
					if (matcher.group().equals("BEGIN") || matcher.group().equals("END"))
						continue;
				this.output += "\\ruby" + this.elementPatterns.get(elementPattern) + " ";
				this.output += matcher.group();
				return toProcess.substring(matcher.end());
			}
		}
		Pattern unknownElement = Pattern.compile(".+?(\\s|~|(\\\\\\\\)|(\\Z))");
		Matcher unknownElementMatcher = unknownElement.matcher(toProcess);
		if (unknownElementMatcher.find()) {
			this.output += unknownElementMatcher.group();
			return toProcess.substring(unknownElementMatcher.end());
		}
		return "";
	}
}
