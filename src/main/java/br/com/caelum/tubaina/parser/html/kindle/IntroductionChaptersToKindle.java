package br.com.caelum.tubaina.parser.html.kindle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.io.TubainaHtmlDir;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import br.com.caelum.tubaina.util.Utilities;
import freemarker.template.Configuration;

public class IntroductionChaptersToKindle {

    private final Parser parser;

    private final Configuration cfg;

    private final TubainaHtmlDir bookRoot;

    static final String RESOURCES_PATH = "introduction-chapters-resources";

    public IntroductionChaptersToKindle(Parser parser, Configuration cfg, TubainaHtmlDir bookRoot) {
        this.parser = parser;
        this.cfg = cfg;
        this.bookRoot = bookRoot;
    }
    
    public String generateIntroductionChapters(List<Chapter> chapters) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parser", parser);
        map.put("sanitizer", new HtmlSanitizer());
        map.put("chapters", chapters);
        for (Chapter chapter : chapters) {
            bookRoot.cd(Utilities.toDirectoryName(RESOURCES_PATH)).writeResources(
                    chapter.getResources());
        }
        StringBuffer processedContent = new FreemarkerProcessor(cfg).process(map, "introductionChapters.ftl");
        String content = processedContent.toString().replace("$$RELATIVE$$", RESOURCES_PATH);
        return content;
    }
    
}
