package br.com.caelum.tubaina.parser;

import br.com.caelum.tubaina.util.Utilities;

public class SimpleIndentator implements Indentator {

	private final String tabReplacement;
	
	public SimpleIndentator(int tabSize) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < tabSize; i++) {
	        stringBuffer.append(" ");
	    }
	    this.tabReplacement = stringBuffer.toString();
    }

    public String indent(String string) {
		string = string.replaceAll("\t", tabReplacement);
		int spaces = Utilities.getMinIndent(string);
		string = removeSpaces(string, spaces);
		return string.trim();
	}

	private String removeSpaces(String string, int size) {
		return string.replaceAll("(?m)(?i)^ {"+size+"}", "");
	}

}
