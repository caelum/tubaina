package br.com.caelum.tubaina.builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookPartParametersExtractor {

    private Pattern bookPartPattern;

    public BookPartParametersExtractor() {
        this.bookPartPattern = Pattern.compile("(?i)(?s)(?m)^\\[part\\s+\"(.*?)\"(.*?)\\].*?");
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

}
