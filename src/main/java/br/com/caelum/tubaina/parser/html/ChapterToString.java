package br.com.caelum.tubaina.parser.html;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class ChapterToString {

	private Parser parser;

	private Configuration cfg;

	private final List<String> dirTree;

	public ChapterToString(Parser parser, Configuration cfg, List<String> dirTree) {
		this.parser = parser;
		this.cfg = cfg;
		this.dirTree = dirTree;
	}

	public StringBuffer generateChapter(Chapter c, int index, int currentDir) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chapter", c);
		map.put("curchap", index);
		map.put("curdir", currentDir);
		map.put("parser", this.parser);
		map.put("dirTree", this.dirTree);
		map.put("sanitizer", new HtmlSanitizer());
		
		return new FreemarkerProcessor(cfg).process(map, "html/chapter.ftl");
	}

}

