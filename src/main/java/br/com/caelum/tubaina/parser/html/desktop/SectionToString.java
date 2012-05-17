package br.com.caelum.tubaina.parser.html.desktop;

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

	public SectionToString(Parser parser, Configuration cfg, List<String> dirTree) {
		this.parser = parser;
		this.cfg = cfg;
		this.dirTree = dirTree;
	}

	public StringBuffer generateSection(Book b, String chapterTitle, int chapterIndex,
			Section s, int sectionIndex, int currentDir) {
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
		map.put("previous", dirTree.get(currentDir - 1));
		if (currentDir+1 < dirTree.size()) {
			map.put("next", dirTree.get(currentDir + 1));	
		} else {
			map.put("next", "");
		}
		
		return new FreemarkerProcessor(cfg).process(map, "section.ftl");
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
		map.put("anchorlink", dirTree.get(currentDir).split("#")[1]);

		return new FreemarkerProcessor(cfg).process(map, "section-flat.ftl");
	}

	public StringBuffer generateSingleHtmlSection(Section s) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("section", s);
		map.put("parser", parser);
		map.put("sanitizer", new HtmlSanitizer());
		return new FreemarkerProcessor(cfg).process(map, "section.ftl");
	}
	
}