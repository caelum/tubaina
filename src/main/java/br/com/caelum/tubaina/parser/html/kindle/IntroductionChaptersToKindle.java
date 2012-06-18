package br.com.caelum.tubaina.parser.html.kindle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class IntroductionChaptersToKindle {

    private final Parser parser;

    private final Configuration cfg;

    public IntroductionChaptersToKindle(Parser parser, Configuration cfg) {
        this.parser = parser;
        this.cfg = cfg;
    }
    
    public StringBuffer generateIntroductionChapters(List<Chapter> chapters) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parser", parser);
        map.put("sanitizer", new HtmlSanitizer());
        map.put("chapters", chapters);
        StringBuffer processedContent = new FreemarkerProcessor(cfg).process(map, "introductionChapters.ftl");
        return processedContent;
    }
    
}
