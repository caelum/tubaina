package br.com.caelum.tubaina.parser;

import br.com.caelum.tubaina.util.Utilities;

public class SimpleIndentator implements Indentator {

	public String indent(String string) {
		string = string.replaceAll("\t", "  ");
		int spaces = Utilities.getMinIndent(string);
		string = removeSpaces(string, spaces);
		return string.trim();
	}

	private String removeSpaces(String string, int size) {
		return string.replaceAll("(?m)(?i)^ {"+size+"}", "");
	}

}
