package br.com.caelum.bibliography;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class BibliographyToHtml {

    private final Bibliography bibliography;
    private final Configuration config;

    public BibliographyToHtml(Bibliography bibliography, Configuration freeMarkerConfig) {
        this.bibliography = bibliography;
        this.config = freeMarkerConfig;
    }

    public String generate() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bibliography", bibliography);
        map.put("sanitizer", new HtmlSanitizer());
        return new FreemarkerProcessor(config).process(map, "bibliography.ftl").toString();
        
    }

}
