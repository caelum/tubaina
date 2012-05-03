package br.com.caelum.tubaina.parser.html.desktop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import br.com.caelum.tubaina.util.VersionGenerator;
import freemarker.template.Configuration;

public class BookToTOC {

	public StringBuffer generateTOC(final Book b, final Configuration cfg, final List<String> dirTree) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", b.getName());
		map.put("chapters", b.getChapters());
		map.put("dirTree", dirTree);
		map.put("textbookVersion", new VersionGenerator().generate());
		map.put("sanitizer", new HtmlSanitizer());

		return new FreemarkerProcessor(cfg).process(map, "toc.ftl");
	}
}
