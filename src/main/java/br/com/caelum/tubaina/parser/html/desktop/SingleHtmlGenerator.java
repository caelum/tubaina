package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.io.HtmlResourceManipulatorFactory;
import br.com.caelum.tubaina.io.ResourceManipulatorFactory;
import br.com.caelum.tubaina.io.TubainaHtmlDir;
import br.com.caelum.tubaina.io.TubainaHtmlIO;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.html.referencereplacer.ReferenceParser;
import br.com.caelum.tubaina.parser.html.referencereplacer.ReferenceReplacer;
import br.com.caelum.tubaina.parser.html.referencereplacer.SingleHtmlChapterReferenceReplacer;
import br.com.caelum.tubaina.parser.html.referencereplacer.SingleHtmlSectionReferenceReplacer;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.Utilities;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlGenerator implements Generator {

	private final Parser parser;
	private final File templateDir;
	private Configuration cfg;
    private List<String> ifdefs;

	public SingleHtmlGenerator(Parser parser, TubainaBuilderData data) {
		this.parser = parser;
		this.templateDir = new File(data.getTemplateDir(), "singlehtml/");
		this.ifdefs = data.getIfdefs();
		configureFreemarker();
	}

	public void generate(Book book, File outputDir) throws IOException {
		StringBuffer bookContent = generateHeader(book);
		
		bookContent.append(new SingleHtmlTOCGenerator(book, cfg).generateTOC());
		
		ResourceManipulatorFactory htmlResourceManipulatorFactory = new HtmlResourceManipulatorFactory();
		
		TubainaHtmlDir bookRoot = new TubainaHtmlIO(templateDir, htmlResourceManipulatorFactory).createTubainaDir(outputDir, book);

		for (Chapter c : book.getChapters()) {
			StringBuffer chapterContent = generateChapter(book, c);
			bookContent.append(chapterContent);
			if (!c.getResources().isEmpty()) {
				bookRoot.cd(Utilities.toDirectoryName(null, c.getTitle()))
						.writeResources(c.getResources());
			}
		}
		
		bookContent = resolveReferencesOf(bookContent);
		
		bookContent.append(generateFooter());

		bookRoot.writeIndex(bookContent);
	}
	
    private StringBuffer resolveReferencesOf(StringBuffer bookContent) {
        List<ReferenceReplacer> replacers = new ArrayList<ReferenceReplacer>();
        
        replacers.add(new SingleHtmlSectionReferenceReplacer());
        replacers.add(new SingleHtmlChapterReferenceReplacer());

        ReferenceParser referenceParser = new ReferenceParser(replacers);

        bookContent = new StringBuffer(referenceParser.replaceReferences(bookContent.toString()));
        return bookContent;
    }
	
	private StringBuffer generateHeader(Book book) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("booktitle", book.getName());
		return new FreemarkerProcessor(cfg).process(map, "book-header.ftl");
	}

	private StringBuffer generateChapter(Book book, Chapter chapter) {
	    StringBuffer chapterContent = new SingleHtmlChapterGenerator(parser, cfg, ifdefs).generateSingleHtmlChapter(book, chapter);
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
		    cfg.setDefaultEncoding("UTF-8");
			cfg.setDirectoryForTemplateLoading(templateDir);
		} catch (IOException e) {
			throw new TubainaException("Couldn't load freemarker template for Single HTML mode", e);
		}
		cfg.setObjectWrapper(new BeansWrapper());
	}
}
