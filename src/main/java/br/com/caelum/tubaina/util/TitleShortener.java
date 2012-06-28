package br.com.caelum.tubaina.util;

public class TitleShortener {

    private static final int MAX_TITLE_LENGTH = 10;

    public String shortenTitle(String title) {
        String shortTitle = title;
        if (shortTitle.length() > MAX_TITLE_LENGTH) {
            shortTitle = shortTitle.substring(0, MAX_TITLE_LENGTH);
            shortTitle = shortTitle + "...";
        }
        return shortTitle;
    }

}
