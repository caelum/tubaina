package br.com.caelum.tubaina.util;

public class TitleShortener {

    private int maxTitleLength;

    public TitleShortener(int maxTitleLength) {
        this.maxTitleLength = maxTitleLength;
    }

    public String shortenTitle(String title) {
        String shortTitle = title;
        if (shortTitle.length() > maxTitleLength) {
            shortTitle = shortTitle.substring(0, maxTitleLength);
            shortTitle = shortTitle + "...";
        }
        return shortTitle;
    }

}
