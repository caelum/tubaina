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

	private static final String DOUBLE_QUOTED_STRING = "dqstring";
	private static final String ARITHMETIC_EXPRESSION = "aritexp";
	private static final String NUMBER = "(((?<=\\+|\\A)[-+])?[0-9]+([.][0-9]+)?([eE][+-]?[0-9]+)?)";
	private static final String BEGIN = "{\n" + "\\small \\noindent \\ttfamily \n";
	private static final String END = "\n}\n";
	
	private final Indentator indentator;
	private final Map<Pattern, String> elementPatterns;
	private String output;
	private String lastMode;

	public RubyTag(Indentator indentator) {
		this.indentator = indentator;
		this.elementPatterns = new HashMap<Pattern, String>();
		this.elementPatterns.put(Pattern.compile("(?m)^\\\\#.*$"), "comment");
		this.elementPatterns.put(Pattern.compile("(?s)^=begin.*?=end"), "comment");
		this.elementPatterns.put(Pattern.compile("(?s)^\".*?(?<!(\\\\char92))\""), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^'.*?(?<!(\\\\char92))'"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%r([^\\p{Alnum}\\[{(]).*?(?<!(\\\\char92))\\1[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%r\\(.*?(?<!(\\\\char92))\\)[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%r\\[.*?(?<!(\\\\char92))\\][eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%r\\{.*?(?<!(\\\\char92))\\}[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^/.*?(?<!(\\\\char92))/[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%s([^\\p{Alnum}\\[{(]).*?(?<!(\\\\char92))\\1"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%s\\(.*?(?<!(\\\\char92))\\)"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%s\\[.*?(?<!(\\\\char92))\\]"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%s\\{.*?(?<!(\\\\char92))\\}"), "symbol");
		this.elementPatterns.put(Pattern.compile("^:(\\\\_|\\p{Alnum})+"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[QW])?([^\\p{Alnum}\\[{(]).*?(?<!(\\\\char92))\\2"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[QW])?\\(.*?(?<!(\\\\char92))\\)"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[QW])?\\[.*?(?<!(\\\\char92))\\]"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[QW])?\\{.*?(?<!(\\\\char92))\\}"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%[qw]([^\\p{Alnum}\\[{(]).*?(?<!(\\\\char92))\\1"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%[qw]\\(.*?(?<!(\\\\char92))\\)"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%[qw]\\[.*?(?<!(\\\\char92))\\]"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%[qw]\\{.*?(?<!(\\\\char92))\\}"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^<<-?([`\"]?)((\\\\_|\\p{Alnum})+)\\1.*?(?<!(\\\\char92))\\2"), "string");
		this.elementPatterns.put(Pattern.compile(
				"^((BEGIN)|(class)|(ensure)|(nil)|(self)|(when)|(END)|(defined\\?)|(def)|" +
				"(false)|(not)|(super)|(while)|(alias)|(defined)|(for)|(or)|(then)|(yield)|" +
				"(and)|(do)|(if)|(redo)|(true)|(begin)|(else)|(in)|(rescue)|(undef)|(break)|" +
				"(elsif)|(module)|(retry)|(unless)|(case)|(end)|(next)|(return)|(until)|(raise))(?!(\\p{Alnum})|(\\\\_)|(\\?))"), "keyword");
		this.elementPatterns.put(Pattern.compile("^((@@)|(@)|(\\\\\\$))(\\\\_|\\p{Alnum})+"), "variable");
		this.elementPatterns.put(Pattern.compile("^[A-Z][\\p{Alnum}_]*"), "constant");
		this.elementPatterns.put(Pattern.compile("^(" + NUMBER + "\\s*[-+*/()]*\\s*)+\\b"), ARITHMETIC_EXPRESSION);
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
		this.lastMode = "";
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
		Pattern spacePattern = Pattern.compile("^(\\s|~|(\\\\\\\\))+");
		Matcher spaceMatcher = spacePattern.matcher(toProcess);
		if (spaceMatcher.find()) {
			this.output += spaceMatcher.group();
			return toProcess.substring(spaceMatcher.end());
		}
		for (Pattern elementPattern : this.elementPatterns.keySet()) {
			Matcher matcher = elementPattern.matcher(toProcess);
			if (matcher.find()) {
				String newMode = this.elementPatterns.get(elementPattern);
				if (newMode.equals("constant")) {
					if (matcher.group().equals("BEGIN") || matcher.group().equals("END")) {
						continue;
					}
				}
				if (newMode.equals(ARITHMETIC_EXPRESSION)) {
					parseArithmeticExpression(matcher.group());
				}
				else if (newMode.equals(DOUBLE_QUOTED_STRING)) {
					parseStringWithInterpolations(matcher.group());
				}
				else {
					this.output += "\\ruby" + newMode + " ";
					this.output += matcher.group();
					this.lastMode = newMode;
				}
				return toProcess.substring(matcher.end());
			}
		}
		Pattern unknownElement = Pattern.compile(".*?(\\s|~|(\\\\\\\\)|(\\Z)|([()])|(\\[)|(\\{)|(::)|(,)|(\\.)|([-+*/]))");
		Matcher unknownElementMatcher = unknownElement.matcher(toProcess);
		if (unknownElementMatcher.find()) {
			if (!this.lastMode.equals("normal")) {
				this.lastMode = "normal";
				this.output += "\\ruby" + this.lastMode + " ";
			}
			this.output += unknownElementMatcher.group();
			return toProcess.substring(unknownElementMatcher.end());
		}
		return "";
	}
	
	private void parseStringWithInterpolations(String string) {
		int lastEnd = 0;
		this.output += "\\rubystring ";
		while (true) {
			int start = findNextInterpolation(string.substring(lastEnd));
			if (start == -1) {
				break;
			}
			start += lastEnd;
			int end = findEndOfInterpolation(string.substring(start)) + start;
			this.output += string.substring(lastEnd, start);
			this.output += "\\#\\{\\rubynormal ";
			String toProcess = string.substring(start + 4, end - 2);
			this.lastMode = "normal";
			while (!toProcess.isEmpty()) {
				toProcess = processNextElement(toProcess);
			}
			if (this.lastMode != "string") {
				this.output += "\\rubystring ";
			}
			this.output += "\\}";
			this.lastMode = "string";
			lastEnd = end;
		}
		this.output += string.substring(lastEnd);
		this.lastMode = "string";
	}
	
	private int findNextInterpolation(String string) {
		Pattern interpolationStart = Pattern.compile("\\\\#\\\\\\{");
		Matcher matcher = interpolationStart.matcher(string);
		if (matcher.find()) {
			return matcher.start();
		}
		return -1;
	}

	private int findEndOfInterpolation(String interpolation) {
		int count = 1;
		int position = 2;
		while (position < interpolation.length() && count > 0) {
			char currentChar = interpolation.charAt(position);
			if (currentChar == '#') {
				if (interpolation.length() > position + 2 && interpolation.charAt(position + 2) == '{') {
					count++;
				}
			}
			else if (currentChar == '}') {
				count--;
			}
			else if (currentChar == '\\') {
				if (interpolation.length() > position + 1 && interpolation.charAt(position + 1) == '\\') {
					position++; // jumps the escaped character
				}
			}
			position++;
		}
		return position;
	}

	private void parseArithmeticExpression(String expression) {
		Pattern numberPattern = Pattern.compile(NUMBER);
		Matcher matcher = numberPattern.matcher(expression);
		int lastEnd = 0;
		boolean matched = matcher.find();
		do {
			this.output += expression.substring(lastEnd, matcher.start());
			this.output += "\\rubynumber " + matcher.group();
			lastEnd = matcher.end();
			matched = matcher.find(lastEnd);
			if (matched) {
				this.output += "\\rubynormal ";
			}
		} while (matched);
		this.lastMode = "number";
	}
}
