package br.com.caelum.tubaina.parser.html;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlGenerator {

	private static final Logger LOG = Logger.getLogger(SingleHtmlGenerator.class);

	public HtmlParser parser;
	public File templateDir;

	public SingleHtmlGenerator(HtmlParser parser, File templateDir) {
		this.parser = parser;
		this.templateDir = new File(templateDir, "singlehtml/");
	}

	public void generate(Book book, File outputFolder) throws IOException {
		Configuration cfg = configureFreemarker();
		
		StringBuffer bookContent = generateHeader(book, cfg);
		for (Chapter c : book.getChapters()) {
			StringBuffer chapterContent = generateChapter(book, cfg, c);
			bookContent.append(chapterContent);
		}
		bookContent.append(generateFooter(cfg));
		new TubainaIO(templateDir).saveFilesForThis(book, outputFolder, bookContent);
	}

	private Configuration configureFreemarker() throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(templateDir);
		cfg.setObjectWrapper(new BeansWrapper());
		return cfg;
	}
	
	private StringBuffer generateHeader(Book book, Configuration cfg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("booktitle", book.getName());
		return new FreemarkerProcessor(cfg).process(map, "book-header.ftl");
	}

	private StringBuffer generateChapter(Book book, Configuration cfg, Chapter c) {
		StringBuffer chapterContent = new ChapterToString(parser, cfg, null).generateSingleHtmlChapter(book, c);
		for (Section s : c.getSections()) {
			if (s.getTitle() != null) { // intro
				StringBuffer sectionContent = new SectionToString(parser, cfg, null).generateSingleHtmlSection(s);
				chapterContent.append(sectionContent);
			}
		}
		return chapterContent;
	}
	
	private StringBuffer generateFooter(Configuration cfg) {
		return new FreemarkerProcessor(cfg).process(new HashMap<String, Object>(), "book-footer.ftl");
	}
}
