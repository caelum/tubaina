package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.Sanitizer;

import com.google.inject.Inject;

public class BoxTag implements Tag<BoxChunk> {

    private static final String TABLE = "<table cellspacing=\"5\" width=\"100%\" border=\"1\" class=\"box-table\"><colgroup><col width=\"550\"/></colgroup><tbody><tr><td/></tr></table>";
	static final String BEGIN = TABLE + "<div class='tubainabox'>";
    static final String END = TABLE + "</div>";
    static final String TITLE_BEGIN = "<div class='tubainabox-title'><b>";
    static final String TITLE_END = "</b></div>\n";
	private final Sanitizer sanitizer;
	private final Parser parser;

	@Inject
    public BoxTag(Sanitizer sanitizer, Parser parser) {
		this.sanitizer = sanitizer;
		this.parser = parser;
	}
    
    @Override
	public String parse(BoxChunk chunk) {
        String title = parser.parse(sanitizer.sanitize(chunk.getTitle().trim()));
		return BEGIN + TITLE_BEGIN + title + TITLE_END + chunk.getContent().trim() + END;
    }

}
