package br.com.caelum.tubaina.parser.html.kindle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

    public String parse(String content, String options) {
    	
    	SimpleIndentator simpleIndentator = new SimpleIndentator();
        String indentedCode = simpleIndentator.indent(content);

        StringBuilder parsedLabel = new StringBuilder();
        parsedLabel.append(matchLabel(options));
        
        return "<pre id=\"" + parsedLabel + "\">\n"
                + indentedCode + "\n</pre>";
    }

    private String matchLabel(String options) {
        Matcher labelMatcher = Pattern.compile("label=(\\S+)").matcher(options);
        if(labelMatcher.find())
        	return labelMatcher.group(1);
        return "";
    }
}
