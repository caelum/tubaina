package br.com.caelum.tubaina.parser.html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class IndexToString {

	private final List<String> dirTree;

	private final Configuration cfg;

	public IndexToString(final List<String> dirTree, final Configuration cfg) {
		this.dirTree = dirTree;
		this.cfg = cfg;
	}

	public StringBuffer createIndex(final Map<String, Integer> indexes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dirTree", dirTree);
		map.put("indexes", indexes);
		map.put("sanitizer", new HtmlSanitizer());
		return new FreemarkerProcessor(cfg).process(map, "html/index.ftl");
	}
	
	public StringBuffer createFlatIndex(final Map<String, Integer> indexes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dirTree", dirTree);
		map.put("indexes", indexes);
		map.put("sanitizer", new HtmlSanitizer());
		return new FreemarkerProcessor(cfg).process(map, "html/index-flat.ftl");
	}


}
