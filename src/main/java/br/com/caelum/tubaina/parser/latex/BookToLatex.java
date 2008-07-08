package br.com.caelum.tubaina.parser.latex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.VersionGenerator;
import freemarker.template.Configuration;

public class BookToLatex {

	private Parser parser;

	public BookToLatex(Parser parser) {
		this.parser = parser;
	}

	public StringBuffer generateLatex(Book book, Configuration cfg) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("book", book);
		map.put("parser", this.parser);
		map.put("textbookVersion", new VersionGenerator().generate());

		FreemarkerProcessor processor = new FreemarkerProcessor(cfg);
		return processor.process(map, "latex/book.ftl");
	}

}
