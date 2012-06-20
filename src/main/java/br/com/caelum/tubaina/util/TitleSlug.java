package br.com.caelum.tubaina.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleSlug {
    private String title;

    public TitleSlug(String title) {
        // The pattern must accept any char except for digits or numbers
        Pattern pattern = Pattern.compile("(?i)(?s)(\\W)+");

        title = title.toLowerCase();

        title = Utilities.removeAccents(title);

        Matcher matcher = pattern.matcher(title);
        title = matcher.replaceAll("-");

        title = title.replaceFirst("-$", "");
        title = title.replaceFirst("^-", "");
        this.title = title;
    }
    
    @Override
    public String toString() {
        return title;
    }
}
