package br.com.caelum.tubaina.parser.html.desktop;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class SingleHtmlChapterGenerator {

    private final Parser parser;

    private final Configuration cfg;

    public SingleHtmlChapterGenerator(final Parser parser, final Configuration cfg) {
        this.parser = parser;
        this.cfg = cfg;
    }
    
    public StringBuffer generateSingleHtmlChapter(Book book, Chapter chapter) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("book", book);
        map.put("chapter", chapter);
        map.put("parser", parser);
        map.put("sanitizer", new HtmlSanitizer());
        return new FreemarkerProcessor(cfg).process(map, "chapter.ftl");
    }
}
