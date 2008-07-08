package br.com.caelum.tubaina.parser.html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class ChapterToString {

	private final Parser parser;

	private final Configuration cfg;

	private final List<String> dirTree;

	public ChapterToString(final Parser parser, final Configuration cfg, final List<String> dirTree) {
		this.parser = parser;
		this.cfg = cfg;
		this.dirTree = dirTree;
	}

	public StringBuffer generateChapter(final Chapter c, final int index, final int currentDir) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chapter", c);
		map.put("curchap", index);
		map.put("curdir", currentDir);
		map.put("parser", parser);
		map.put("dirTree", dirTree);
		map.put("sanitizer", new HtmlSanitizer());

		return new FreemarkerProcessor(cfg).process(map, "html/chapter.ftl");
	}

}
