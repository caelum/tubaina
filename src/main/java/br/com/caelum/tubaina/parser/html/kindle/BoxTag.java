package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag {

    static final String BEGIN = "<table cellspacing=\"5\" width=\"100%\" border=\"1\" class=\"box-table\"><colgroup>" +
            "<col width=\"550\"/></colgroup><tbody><tr><td/></tr></table>";
    static final String END = BEGIN;
    static final String TITLE_BEGIN = "<b>";
    static final String TITLE_END = "</b>\n";
    
    public String parse(String content, String title) {
        return BEGIN + TITLE_BEGIN + title.trim() + TITLE_END + content.trim() + END;
    }

}
