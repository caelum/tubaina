package br.com.caelum.tubaina.parser.latex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.CodeHighlightTag;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class RubyTag implements Tag {

	private static final String BEGIN = "{\n" + "\\small \\noindent \\ttfamily \n";
	private static final String END = "\n}\n";
	private static final String IDENTIFIER = "((\\\\_)|(\\p{Alnum}))+";
	private static final String DOUBLE_QUOTED_STRING = "dqstring";
	private static final String EXECUTION_STRING = "execstring";
	private static final String ARITHMETIC_EXPRESSION = "aritexp";
	private static final String LINE_ORIENTED_STRING = "lostring";
	private static final String NUMBER = "(((?<=\\+|\\A)[-+])?[0-9]+([.][0-9]+)?([eE][+-]?[0-9]+)?)";
	private static final String LINE_ORIENTED_STRING_TAG = "<<-?([`\"']?)(" + IDENTIFIER + ")\\1(,~*)?";
	
	private final Indentator indentator;
	private final Map<Pattern, String> elementPatterns;
	private String output;
	private String lastMode;

	public RubyTag(Indentator indentator) {
		this.indentator = indentator;
		this.elementPatterns = new HashMap<Pattern, String>();
		this.elementPatterns.put(Pattern.compile("(?m)\\A\\\\#.*((?=\\\\\\\\$)|(\\Z))"), "comment");
		this.elementPatterns.put(Pattern.compile("(?s)^=begin.*?=end"), "comment");
		this.elementPatterns.put(Pattern.compile("(?s)^\".*?(?<!(\\\\char92))\""), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^`.*?(?<!\\\\)`"), EXECUTION_STRING);
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
		this.elementPatterns.put(Pattern.compile("^:" + IDENTIFIER + "\\??"), "symbol");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[QW])?([^\\p{Alnum}\\[{(]).*?(?<!(\\\\char92))\\2"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[QW])?\\(.*?(?<!(\\\\char92))\\)"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[QW])?\\[.*?(?<!(\\\\char92))\\]"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%([^\\p{Alnum}\\s]|[QW])?\\{.*?(?<!(\\\\char92))\\}"), DOUBLE_QUOTED_STRING);
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%[qw]([^\\p{Alnum}\\[{(]).*?(?<!(\\\\char92))\\1"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%[qw]\\(.*?(?<!(\\\\char92))\\)"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%[qw]\\[.*?(?<!(\\\\char92))\\]"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^\\\\%[qw]\\{.*?(?<!(\\\\char92))\\}"), "string");
		this.elementPatterns.put(Pattern.compile("(?s)^(?=" + LINE_ORIENTED_STRING_TAG + ")"), LINE_ORIENTED_STRING);
		this.elementPatterns.put(Pattern.compile(
				"^((BEGIN)|(class)|(ensure)|(nil)|(self)|(when)|(END)|(defined\\?)|(def)|" +
				"(false)|(not)|(super)|(while)|(alias)|(defined)|(for)|(or)|(then)|(yield)|" +
				"(and)|(do)|(if)|(redo)|(true)|(begin)|(else)|(in)|(rescue)|(undef)|(break)|" +
				"(elsif)|(module)|(retry)|(unless)|(case)|(end)|(next)|(return)|(until)|(raise))(?!(\\p{Alnum})|(\\\\_)|(\\?))"), "keyword");
		this.elementPatterns.put(Pattern.compile("^((@@)|(@))" + IDENTIFIER), "variable");
		this.elementPatterns.put(Pattern.compile("^\\\\\\$" + IDENTIFIER), "global");
		this.elementPatterns.put(Pattern.compile("^[A-Z]((\\p{Alnum})|(\\\\_))*"), "constant");
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
		toProcess = codeHighlightTag.parseLatex(toProcess, highlights);
		this.output = "";
		this.lastMode = "";
		while (!toProcess.equals("")) {
			toProcess = processNextElement(toProcess);
		}
		this.output = parseSymbols(this.output);
		this.output = addIndentationCommandsWhereNeccessary(this.output);
		return BEGIN + output + END;
	}
	
	private String addIndentationCommandsWhereNeccessary(String code) {
		String result = "";
		Matcher lineMatcher = Pattern.compile("(?m).*(\\n|\\Z)").matcher(code);
		while (lineMatcher.find()) {
			String line = lineMatcher.group();
			if (line.startsWith("~")) {
				result += "\\rubynormal ";
				Matcher commandMatcher = Pattern.compile("\\\\ruby(\\w+) ").matcher(line);
				// First command of line is \rubynormal; remove it
				if (commandMatcher.find() && commandMatcher.group(1).equals("normal")) {
					result += commandMatcher.replaceFirst("");
				} else {
					result += line;
				}
			} else {
				result += line;
			}
		}
		return result;
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
					this.output += "\\rubystring ";
					parseStringWithInterpolations(matcher.group(), "string");
				}
				else if (newMode.equals(EXECUTION_STRING)) {
					this.output += "\\rubyexecution ";
					parseStringWithInterpolations(matcher.group(), "execution");
				}
				else if (newMode.equals(LINE_ORIENTED_STRING)) {
					return parseLineOrientedString(toProcess);
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
	
	private String parseLineOrientedString(String code) {
		Queue<String> tags = new LinkedList<String>();
		Queue<String> delimiters = new LinkedList<String>();
		Pattern pattern = Pattern.compile(LINE_ORIENTED_STRING_TAG);
		String[] parts = code.split("\\\\\\\\\n", 2);
		String firstLine = parts[0];
		String body = parts[1];
		Matcher matcher = pattern.matcher(firstLine);
		while (matcher.find()) {
			String currentDelimiter = matcher.group(1);
			tags.add(matcher.group(2));
			delimiters.add(currentDelimiter);
			if (currentDelimiter.equals("`")) {
				this.output += "\\rubyexecution ";
			}
			else {
				this.output += "\\rubystring ";
			}
			this.output += matcher.group();
		}
		this.output += "\\rubynormal \\\\\n";
		this.lastMode = "normal";
		while (!tags.equals("")) {
			String currentTag = tags.poll();
			String currentDelimiter = delimiters.poll();
			Pattern endTagPattern = Pattern.compile("(?m)^" + currentTag + "((\\\\\\\\\n)|(\\Z))");
			Matcher endTagMatcher = endTagPattern.matcher(body);
			if (!endTagMatcher.find()) {
				// ignores the error in the code: simply add
				// the remaining text as string
				break;
			}
			int endOfTag = endTagMatcher.start();
			String stringForCurrentTag = body.substring(0, endOfTag);
			if (currentDelimiter.equals("`")) {
				this.output += "\\rubyexecution ";
			}
			else {
				this.output += "\\rubystring ";
			}
			if (currentDelimiter.equals("'")) {
				this.output += stringForCurrentTag;
			}
			else {
				String mode;
				if (currentDelimiter.equals("`")) {
					mode = "execution";
				}
				else {
					mode = "string";
				}
				parseStringWithInterpolations(stringForCurrentTag, mode);
			}
			this.output += currentTag + "\\rubynormal \\\\\n";
			body = body.substring(endTagMatcher.end());
		}
		return body;
	}
	
	private void parseStringWithInterpolations(String string, String outsideMode) {
		int lastEnd = 0;
		this.lastMode = outsideMode;
		while (true) {
			int start = findNextInterpolation(string.substring(lastEnd));
			if (start == -1) {
				break;
			}
			start += lastEnd;
			int end = findEndOfInterpolation(string.substring(start)) + start;
			this.output += string.substring(lastEnd, start);
			this.output += "\\#\\{";
			String toProcess = string.substring(start + 4, end - 2);
			while (!toProcess.equals("")) {
				toProcess = processNextElement(toProcess);
			}
			if (this.lastMode != outsideMode) {
				this.output += "\\ruby" + outsideMode + " ";
			}
			this.output += "\\}";
			this.lastMode = outsideMode;
			lastEnd = end;
		}
		this.output += string.substring(lastEnd);
		this.lastMode = outsideMode;
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
