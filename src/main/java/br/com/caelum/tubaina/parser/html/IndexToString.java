package br.com.caelum.tubaina.parser.html;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class IndexToString {

	private final List<String> dirTree;
	private final Configuration cfg;

	public IndexToString(List<String> dirTree, Configuration cfg) {
		this.dirTree = dirTree;
		this.cfg = cfg;
	}

	public StringBuffer createIndex(Map<String, Integer> indexes) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dirTree", this.dirTree);
		map.put("indexes", indexes);
		map.put("sanitizer", new HtmlSanitizer());
		return new FreemarkerProcessor(cfg).process(map, "html/index.ftl");
	}

}
