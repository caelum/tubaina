package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Tag;

public class BoxTag implements Tag<BoxChunk> {

    static final String BEGIN = "<table cellspacing=\"5\" width=\"100%\" border=\"1\" class=\"box-table\"><colgroup>" +
            "<col width=\"550\"/></colgroup><tbody><tr><td/></tr></table>";
    static final String END = BEGIN;
    static final String TITLE_BEGIN = "<b>";
    static final String TITLE_END = "</b>\n";
    
    @Override
	public String parse(BoxChunk chunk) {
        return BEGIN + TITLE_BEGIN + chunk.getTitle().trim() + TITLE_END + chunk.getContent().trim() + END;
    }

}
