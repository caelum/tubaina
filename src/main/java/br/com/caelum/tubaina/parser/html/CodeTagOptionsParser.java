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

    public String parseLanguage(String options) {
        if (options != null) {
            String languageCandidate = options.trim().split(" ")[0];
            if (!languageCandidate.equals("#") && !languageCandidate.startsWith("h=")
                    && !languageCandidate.startsWith("label=") && !languageCandidate.isEmpty()
                    && !languageCandidate.contains("filename="))
                return languageCandidate;
        }
        return "text";
    }

    public String parseLabel(String options) {
        Matcher labelMatcher = Pattern.compile("label=(\\S+)").matcher(options);
        if (labelMatcher.find()) {
            return labelMatcher.group(1);
        }
        return "";
    }

    public String parseFileName(String options) {
        String filename = "";
        Matcher filenameMatcher = Pattern.compile("filename=(\\S+)").matcher(options);
        if (filenameMatcher.find()) {
            filename = filenameMatcher.group(1);
        }
        return filename;
    }

}
