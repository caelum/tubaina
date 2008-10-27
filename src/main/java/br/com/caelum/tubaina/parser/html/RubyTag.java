package br.com.caelum.tubaina.parser.html;

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
	private static final String BEGIN = "<div class=\"ruby\"><code class=\"ruby\">\n";
	private static final String END = "</code></div>\n";
	
	private final Indentator indentator;
	private final String identifier = "[\\p{Alnum}_]+";
	private final Map<Pattern, String> elementPatterns;
	private String output;

	public RubyTag(Indentator indentator) {
		this.indentator = indentator;
		this.elementPatterns = new HashMap<Pattern, String>();
		this.elementPatterns.put(Pattern.compile("(?m)^#.*$"), "comment");
		this.elementPatterns.put(Pattern.compile("(?s)^=begin.*?=end"), "comment");
		this.elementPatterns.put(Pattern.compile("(?s)^\".*?(?<!\\\\)\""), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^'.*?(?<!\\\\)'"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^%r([^\\p{Alnum}\\[{(]).*?(?<!\\\\)\\1[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^%r\\(.*?(?<!\\\\)\\)[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^%r\\[.*?(?<!\\\\)\\][eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^%r\\{.*?(?<!\\\\)\\}[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^/.*?(?<!\\\\)/[eimnosux]*"), "regexp");
		this.elementPatterns.put(Pattern.compile("(?s)^%s([^\\p{Alnum}\\[{(]).*?(?<!\\\\)\\1"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^%s\\(.*?(?<!\\\\)\\)"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^%s\\[.*?(?<!\\\\)\\]"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^%s\\{.*?(?<!\\\\)\\}"), "symbol");
		this.elementPatterns.put(Pattern.compile("^:" + identifier), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^%([^\\p{Alnum}\\s]|[QW])?([^\\p{Alnum}\\[{(]).*?(?<!\\\\)\\2"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^%([^\\p{Alnum}\\s]|[QW])?\\(.*?(?<!\\\\)\\)"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^%([^\\p{Alnum}\\s]|[QW])?\\[.*?(?<!\\\\)\\]"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^%([^\\p{Alnum}\\s]|[QW])?\\{.*?(?<!\\\\)\\}"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^%[qw]([^\\p{Alnum}\\[{(]).*?(?<!\\\\)\\1"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^%[qw]\\(.*?(?<!\\\\)\\)"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^%[qw]\\[.*?(?<!\\\\)\\]"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^%[qw]\\{.*?(?<!\\\\)\\}"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^&lt;&lt;-?([`\"]?)(" + identifier + ")\\1.*?\\\n\\2\\\n?"), "string");
		this.elementPatterns.put(Pattern.compile(
				"^((BEGIN)|(class)|(ensure)|(nil)|(self)|(when)|(END)|(defined\\?)|(def)|" +
				"(false)|(not)|(super)|(while)|(alias)|(defined)|(for)|(or)|(then)|(yield)|" +
				"(and)|(do)|(if)|(redo)|(true)|(begin)|(else)|(in)|(rescue)|(undef)|(break)|" +
				"(elsif)|(module)|(retry)|(unless)|(case)|(end)|(next)|(return)|(until)|(raise))(?![\\p{Alnum}_?])"), "keyword");
		this.elementPatterns.put(Pattern.compile("^((@@)|(@)|(\\$))" + identifier), "variable");
		this.elementPatterns.put(Pattern.compile("^[A-Z][\\p{Alnum}_]*"), "constant");
		this.elementPatterns.put(Pattern.compile("^(" + NUMBER + "\\s*[-+*/()]*\\s*)+\\b"), ARITHMETIC_EXPRESSION);
		this.elementPatterns.put(Pattern.compile("^0[xX][A-Fa-f0-9]+\\b"), "number");
		this.elementPatterns.put(Pattern.compile("^0[bB][01]+\\b"), "number");
	}

	public String parse(String code, String options) {
		this.output = "";
		CodeHighlightTag codeHighlightTag = new CodeHighlightTag();
		List<Integer> highlights = codeHighlightTag.getHighlights(options);
		String toProcess = this.parseSpaces(this.indentator.indent(code));
		while (!toProcess.isEmpty()) {
			toProcess = processNextElement(toProcess);
		}
		output = codeHighlightTag.parseHtml(output, highlights);
		return BEGIN + output + END;
	}
	
	private String parseSpaces(String string) {
		string = string.replaceAll("<", "&lt;");
		string = string.replaceAll(">", "&gt;");
		string = string.replaceAll(" ", "&nbsp;");
		string = string.replaceAll("\n", "<br/>\n");
		string = string.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		return string;
	}

	private String processNextElement(String toProcess) {
		Pattern spacePattern = Pattern.compile("^((\\s)|(&nbsp;)|(<br/>))+");
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
					this.output += "<span class=\"ruby" + newMode + "\">";
					this.output += matcher.group();
					this.output += "</span>";
				}
				return toProcess.substring(matcher.end());
			}
		}
		Pattern unknownElement = Pattern.compile(".*?((\\s)|(&nbsp;)|(<br/>)|(\\Z)|(\\()|(\\[)|(\\{)|(::)|(,)|(\\.)|([-+*/]))");
		Matcher unknownElementMatcher = unknownElement.matcher(toProcess);
		if (unknownElementMatcher.find()) {
			this.output += unknownElementMatcher.group();
			return toProcess.substring(unknownElementMatcher.end());
		}
		return "";
	}
	
	private void parseStringWithInterpolations(String string) {
		int lastEnd = 0;
		this.output += "<span class=\"rubystring\">";
		while (true) {
			int start = findNextInterpolation(string.substring(lastEnd));
			if (start == -1) {
				break;
			}
			start += lastEnd;
			int end = findEndOfInterpolation(string.substring(start)) + start;
			this.output += string.substring(lastEnd, start);
			this.output += "#{<span class=\"rubynormal\">";
			String toProcess = string.substring(start + 2, end - 1);
			while (!toProcess.isEmpty()) {
				toProcess = processNextElement(toProcess);
			}
			this.output += "</span>}";
			lastEnd = end;
		}
		this.output += string.substring(lastEnd);
		this.output += "</span>";
	}
	
	private int findNextInterpolation(String string) {
		Pattern interpolationStart = Pattern.compile("(?<!\\\\)#\\{");
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
				if (interpolation.length() > position + 1 && interpolation.charAt(position + 1) == '{') {
					count++;
				}
			}
			else if (currentChar == '}') {
				count--;
			}
			else if (currentChar == '\\') {
				position++; // jumps the escaped character
			}
			position++;
		}
		return position;
	}

	private void parseArithmeticExpression(String expression) {
		Pattern numberPattern = Pattern.compile(NUMBER);
		Matcher matcher = numberPattern.matcher(expression);
		int lastEnd = 0;
		while (matcher.find(lastEnd)) {
			this.output += expression.substring(lastEnd, matcher.start());
			this.output += "<span class=\"rubynumber\">" + matcher.group() + "</span>";
			lastEnd = matcher.end();
		}
	}
}