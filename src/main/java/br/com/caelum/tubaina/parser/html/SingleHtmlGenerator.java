package br.com.caelum.tubaina.parser.html;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.io.TubainaDir;
import br.com.caelum.tubaina.io.TubainaHtmlIO;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlGenerator {

	private static final Logger LOG = Logger.getLogger(SingleHtmlGenerator.class);

	private HtmlParser parser;
	private File templateDir;
	private Configuration cfg;


	public SingleHtmlGenerator(HtmlParser parser, File templateDir) {
		this.parser = parser;
		this.templateDir = new File(templateDir, "singlehtml/");
		configureFreemarker();
	}

	public void generate(Book book, File outputDir) throws IOException {
		List<Resource> resources = new ArrayList<Resource>();
		StringBuffer bookContent = generateHeader(book);
		for (Chapter c : book.getChapters()) {
			StringBuffer chapterContent = generateChapter(book, c);
			bookContent.append(chapterContent);
			resources.addAll(c.getResources());
		}
		bookContent.append(generateFooter());

		TubainaDir bookRoot = new TubainaHtmlIO(templateDir).createTubainaDir(outputDir, book);
		bookRoot.writeIndex(bookContent)
				.writeResources(resources);
	}
	
	private StringBuffer generateHeader(Book book) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("booktitle", book.getName());
		return new FreemarkerProcessor(cfg).process(map, "book-header.ftl");
	}

	private StringBuffer generateChapter(Book book, Chapter c) {
		StringBuffer chapterContent = new ChapterToString(parser, cfg, null).generateSingleHtmlChapter(book, c);
		for (Section s : c.getSections()) {
			if (s.getTitle() != null) { // intro
				StringBuffer sectionContent = new SectionToString(parser, cfg, null).generateSingleHtmlSection(s);
				chapterContent.append(sectionContent);
			}
		}
		return chapterContent;
	}
	
	private StringBuffer generateFooter() {
		return new FreemarkerProcessor(cfg).process(new HashMap<String, Object>(), "book-footer.ftl");
	}
	
	private void configureFreemarker() {
		cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(templateDir);
		} catch (IOException e) {
			throw new TubainaException("Couldn't load freemarker template for Single HTML mode", e);
		}
		cfg.setObjectWrapper(new BeansWrapper());
	}
}
