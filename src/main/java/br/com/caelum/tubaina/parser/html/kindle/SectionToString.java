package br.com.caelum.tubaina.parser.html.kindle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class SectionToString {

    private final Parser parser;

    private final Configuration cfg;

    private final List<String> dirTree;

    public SectionToString(Parser parser, Configuration cfg, List<String> dirTree) {
        this.parser = parser;
        this.cfg = cfg;
        this.dirTree = dirTree;
    }

    public StringBuffer generateKindleHtmlSection(Section section, Chapter chapter,
            int sectionNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("section", section);
        map.put("parser", parser);
        map.put("chapter", chapter);
        map.put("sanitizer", new HtmlSanitizer());
        map.put("sectionNumber", sectionNumber);
        return new FreemarkerProcessor(cfg).process(map, "section.ftl");
    }
}
