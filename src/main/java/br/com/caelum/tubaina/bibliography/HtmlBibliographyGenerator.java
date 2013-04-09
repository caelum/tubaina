package br.com.caelum.tubaina.bibliography;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class HtmlBibliographyGenerator implements BibliographyGenerator {

    private final Configuration config;

    public HtmlBibliographyGenerator(Configuration freeMarkerConfig) {
        this.config = freeMarkerConfig;
    }

    public String generateTextOf(Bibliography bibliography) {
    	if (bibliography.isEmpty()) {
    		return "";
    	}
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bibliography", bibliography);
        map.put("sanitizer", new HtmlSanitizer());
        return new FreemarkerProcessor(config).process(map, "bibliography.ftl").toString();
        
    }

}
