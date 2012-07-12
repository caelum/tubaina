package br.com.caelum.tubaina.parser;

import br.com.caelum.tubaina.util.Utilities;

public class SimpleIndentator implements Indentator {

	public static final String TAB_REPLACEMENT = "  ";

    public String indent(String string) {
		string = string.replaceAll("\t", TAB_REPLACEMENT);
		int spaces = Utilities.getMinIndent(string);
		string = removeSpaces(string, spaces);
		return string.trim();
	}

	private String removeSpaces(String string, int size) {
		return string.replaceAll("(?m)(?i)^ {"+size+"}", "");
	}

}
