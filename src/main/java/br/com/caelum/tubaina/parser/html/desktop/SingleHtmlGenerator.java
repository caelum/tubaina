package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.io.HtmlResourceManipulatorFactory;
import br.com.caelum.tubaina.io.ResourceManipulatorFactory;
import br.com.caelum.tubaina.io.TubainaHtmlDir;
import br.com.caelum.tubaina.io.TubainaHtmlIO;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.Utilities;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlGenerator implements Generator {

	private final Parser parser;
	private final File templateDir;
	private Configuration cfg;

	public SingleHtmlGenerator(Parser parser, TubainaBuilderData data) {
		this.parser = parser;
		this.templateDir = new File(data.templateDir, "singlehtml/");
		configureFreemarker();
	}

	public void generate(Book book, File outputDir) throws IOException {
		StringBuffer bookContent = generateHeader(book);
		
		ResourceManipulatorFactory htmlResourceManipulatorFactory = new HtmlResourceManipulatorFactory();
		
		TubainaHtmlDir bookRoot = new TubainaHtmlIO(templateDir, htmlResourceManipulatorFactory).createTubainaDir(outputDir, book);

		for (Chapter c : book.getChapters()) {
			StringBuffer chapterContent = generateChapter(book, c);
			bookContent.append(chapterContent);
			if(!c.getResources().isEmpty()) {
				bookRoot.cd(Utilities.toDirectoryName(null, c.getTitle()))
						.writeResources(c.getResources());
			}
		}
		bookContent.append(generateFooter());

		bookRoot.writeIndex(bookContent);
	}
	
	private StringBuffer generateHeader(Book book) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("booktitle", book.getName());
		return new FreemarkerProcessor(cfg).process(map, "book-header.ftl");
	}

	private StringBuffer generateChapter(Book book, Chapter chapter) {
	    StringBuffer sectionsContent = new StringBuffer();
	    for (Section section : chapter.getSections()) {
	        if (section.getTitle() != null) { // intro
	            StringBuffer sectionContent = new SectionToString(parser, cfg, null).generateSingleHtmlSection(section);
	            sectionsContent.append(sectionContent);
	        }
	    }
	    StringBuffer chapterContent = new SingleHtmlChapterGenerator(parser, cfg).generateSingleHtmlChapter(book, chapter, sectionsContent.toString());
		return fixPaths(chapter, chapterContent);
	}

	private StringBuffer fixPaths(Chapter chapter, StringBuffer chapterContent) {
		String chapterName = Utilities.toDirectoryName(null, chapter.getTitle());
		return new StringBuffer(chapterContent.toString().replace("$$RELATIVE$$", chapterName));
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
