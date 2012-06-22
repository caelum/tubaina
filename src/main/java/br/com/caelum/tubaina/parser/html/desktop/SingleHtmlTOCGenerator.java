package br.com.caelum.tubaina.parser.html.desktop;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class SingleHtmlTOCGenerator {

    private final Book book;
    private final Configuration cfg;

    public SingleHtmlTOCGenerator(Book book, Configuration cfg) {
        this.book = book;
        this.cfg = cfg;
    }

    public StringBuffer generateTOC() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("book", book);
        map.put("sanitizer", new HtmlSanitizer());
        return new FreemarkerProcessor(cfg).process(map, "toc.ftl");
    }
    
    

}
