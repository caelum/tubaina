package br.com.caelum.tubaina.builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookPartParametersExtractor {

    private Pattern bookPartPattern;
    private Pattern illustrationPattern;

    public BookPartParametersExtractor() {
        this.bookPartPattern = Pattern.compile("(?i)(?s)(?m)^\\[part\\s+\"(.*?)\"(.*?)\\].*?");
        this.illustrationPattern = Pattern.compile("(?i)(?s)(?m)^\\[part(.*?)illustration=([\\S]+)(\\S*?)\\].*?");
    }

    public String extractTitleFrom(String text) {
        Matcher titleMatcher = bookPartPattern.matcher(text);
        titleMatcher.find();
        return titleMatcher.group(1).trim();
    }

    public boolean containsPartTag(String text) {
        Matcher chapterMatcher = bookPartPattern.matcher(text);
        return chapterMatcher.matches();
    }

    public String extractIllustrationPathFrom(String text) {
        if (!containsIllustration(text))
            return "";
        Matcher illustrationMatcher = illustrationPattern.matcher(text);
        illustrationMatcher.find();
        return illustrationMatcher.group(2).trim();
    }
    
    public boolean containsIllustration(String text) {
        Matcher illustrationMatcher = illustrationPattern.matcher(text);
        return illustrationMatcher.matches();
    }

}
