package br.com.caelum.tubaina.parser.html.kindle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class ChapterToString {
    private final Parser parser;

    private final Configuration cfg;

    private final List<String> dirTree;

    public ChapterToString(final Parser parser, final Configuration cfg, final List<String> dirTree) {
        this.parser = parser;
        this.cfg = cfg;
        this.dirTree = dirTree;
    }

    public StringBuffer generateKindleHtmlChapter(Book book, Chapter chapter,
            StringBuffer allSectionsContent) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("book", book);
        map.put("chapter", chapter);
        map.put("allSectionsContent", allSectionsContent);
        map.put("parser", parser);
        map.put("sanitizer", new HtmlSanitizer());
        return new FreemarkerProcessor(cfg).process(map, "chapter.ftl");
    }

}
