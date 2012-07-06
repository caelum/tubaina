package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag {

    static final String BEGIN = "<hr /><td>";
    static final String END = "<hr />";
    static final String TITLE_BEGIN = "<b>";
    static final String TITLE_END = "</b>\n";

    public String parse(String content, String title) {
        return BEGIN + TITLE_BEGIN + title.trim() + TITLE_END + content.trim()
                + END;
    }

}
