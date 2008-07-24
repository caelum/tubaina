package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Escape {

	BACKSLASH("\\","\\char92"),
	SHARP("#", "\\#"),
	DOLLAR("$", "\\$"),
	OPEN("{", "\\{"),
	CLOSE("}", "\\}"),
	DOUBLE_UNDERLINE("__", "!#!#!"),
	UNDERLINE("_", "\\_"),
	UNDOUBLE_UNDERLINE("!#!#!", "__"),
	DOUBLE_PERCENT("%%", "!#!#!"),//workaround
	PERCENT("%","\\%"),
	UNDOUBLE_PERCENT("!#!#!", "%%"),//workaround
	AMPERSAND("&", "\\&"),
	HAT("^", "\\char94"),
	TILDE("~", "\\char126"),
	QUOTE("(?s)(?i)(\\[quote\\s*.+?\\s*)--(\\s*.+?\\s*\\])","$1#!#!$2" ),//workaround
	HYPHEN("--", "{\\verb#-#}{\\verb#-#}"),
	UNQUOTE("#!#!","--" ),
	SHIFT_LEFT("<<", "{\\verb#<#}{\\verb#<#}"),
	SHIFT_RIGHT(">>", "{\\verb#>#}{\\verb#>#}");//workaround
	
	private String to;
	private String from;

	Escape(String from, String to) {
		this.from = from;
		this.to = to;
	}
	
	public String escape(String content) {
		if (this == QUOTE) return content.replaceAll(from, to);
		return content.replaceAll(Pattern.quote(from), Matcher.quoteReplacement(to));
	}
	public String unescape(String content) {
		return content.replaceAll(Pattern.quote(to), Matcher.quoteReplacement(from));
	}
	
}
