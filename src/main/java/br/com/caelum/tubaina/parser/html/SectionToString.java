package br.com.caelum.tubaina.parser.html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import freemarker.template.Configuration;

public class SectionToString {

	private final Parser parser;

	private final Configuration cfg;

	private final List<String> dirTree;

	public SectionToString(final Parser parser, final Configuration cfg, final List<String> dirTree) {
		this.parser = parser;
		this.cfg = cfg;
		this.dirTree = dirTree;
	}

	public StringBuffer generateSection(final Book b, final String chapterTitle, final int chapterIndex,
			final Section s, final int sectionIndex, final int currentDir) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("book", b);
		map.put("title", chapterTitle);
		map.put("section", s);
		map.put("curchap", chapterIndex);
		map.put("cursec", sectionIndex);
		map.put("parser", parser);
		map.put("dirTree", dirTree);
		map.put("curdir", currentDir);
		map.put("sanitizer", new HtmlSanitizer());

		return new FreemarkerProcessor(cfg).process(map, "html/section.ftl");
	}
	
	public StringBuffer generateFlatSection(final Book b, final String chapterTitle, final int chapterIndex,
			final Section s, final int sectionIndex, final int currentDir) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("book", b);
		map.put("title", chapterTitle);
		map.put("section", s);
		map.put("curchap", chapterIndex);
		map.put("cursec", sectionIndex);
		map.put("parser", parser);
		map.put("dirTree", dirTree);
		map.put("curdir", currentDir);
		map.put("sanitizer", new HtmlSanitizer());

		return new FreemarkerProcessor(cfg).process(map, "html/section-flat.ftl");
	}

}