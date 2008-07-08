package br.com.caelum.tubaina.parser.latex;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.VersionGenerator;
import freemarker.template.Configuration;

public class BookToLatex {

	private final Parser parser;

	public BookToLatex(final Parser parser) {
		this.parser = parser;
	}

	public StringBuffer generateLatex(final Book book, final Configuration cfg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("book", book);
		map.put("parser", parser);
		map.put("textbookVersion", new VersionGenerator().generate());

		FreemarkerProcessor processor = new FreemarkerProcessor(cfg);
		return processor.process(map, "latex/book.ftl");
	}

}
