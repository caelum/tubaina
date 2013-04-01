package br.com.caelum.tubaina.parser.html.kindle;

import com.google.inject.Inject;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.Sanitizer;

public class BoxTag implements Tag<BoxChunk> {

    static final String BEGIN = "<table cellspacing=\"5\" width=\"100%\" border=\"1\" class=\"box-table\"><colgroup>" +
            "<col width=\"550\"/></colgroup><tbody><tr><td/></tr></table>";
    static final String END = BEGIN;
    static final String TITLE_BEGIN = "<b>";
    static final String TITLE_END = "</b>\n";
	private final Sanitizer sanitizer;

	@Inject
    public BoxTag(Sanitizer sanitizer) {
		this.sanitizer = sanitizer;
	}
    
    @Override
	public String parse(BoxChunk chunk) {
        String title = sanitizer.sanitize(chunk.getTitle().trim());
		return BEGIN + TITLE_BEGIN + title + TITLE_END + chunk.getContent().trim() + END;
    }

}
