package br.com.caelum.tubaina.parser.html.kindle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

    public String parse(String content, String options) {
        String indentedCode = content.replaceAll(" ", "&nbsp;");
        indentedCode = indentedCode.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        
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
