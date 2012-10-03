package br.com.caelum.tubaina.parser.html;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeTagOptionsParser {

    public List<Integer> parseHighlights(String options) {
        ArrayList<Integer> lines = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("h=([\\d+,]+)");
        Matcher matcher = pattern.matcher(options);
        if (matcher.find()) {
            String[] strings = matcher.group(1).split(",");
            for (String string : strings) {
                lines.add(Integer.parseInt(string));
            }
        }
        return lines;
    }

    
}
