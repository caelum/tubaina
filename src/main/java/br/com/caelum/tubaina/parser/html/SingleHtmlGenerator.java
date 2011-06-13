package br.com.caelum.tubaina.parser.html;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.resources.HtmlResourceManipulator;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceManipulator;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.FileUtilities;
import br.com.caelum.tubaina.util.Utilities;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlGenerator {

	private static final Logger LOG = Logger.getLogger(SingleHtmlGenerator.class);

	private final HtmlParser parser;

	private final File templateDir;

	public SingleHtmlGenerator(HtmlParser parser, File templateDir) {
		this.parser = parser;
		this.templateDir = new File(templateDir, "singlehtml/");
	}

	public void generate(Book book, File outputFolder) throws IOException {
		// FreeMarker configuration
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(templateDir);
		cfg.setObjectWrapper(new BeansWrapper());
		
		StringBuffer bookContent = createBookHeader(book, cfg);
		
		for (Chapter c : book.getChapters()) {
			StringBuffer chapterContent = new ChapterToString(parser, cfg, null).generateSingleHtmlChapter(book, c);
			for (Section s : c.getSections()) {
				if (s.getTitle() != null) { // intro
					StringBuffer sectionContent = new SectionToString(parser, cfg, null).generateSingleHtmlSection(s);
					chapterContent.append(sectionContent);
				}
			}
			bookContent.append(chapterContent);
		}
		bookContent.append(new FreemarkerProcessor(cfg).process(new HashMap<String, Object>(), "book-footer.ftl"));
		buildOutputDirsAndFiles(book, outputFolder, bookContent);
	}

	private void buildOutputDirsAndFiles(Book book, File outputFolder, StringBuffer bookContent) throws IOException {
		File bookRoot = new File(outputFolder, Utilities.toDirectoryName(null, book.getName()));
		bookRoot.mkdir();
		
		saveToFile(bookRoot, bookContent);
		copyResources(book, bookRoot);
	}

	private File saveToFile(File directory, StringBuffer sb) throws IOException {
		File file = new File(directory, "index.html");
		PrintStream ps = new PrintStream(file, "UTF-8");
		ps.print(sb.toString());
		ps.close();
		return directory;
	}
	
	private StringBuffer createBookHeader(Book book, Configuration cfg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("booktitle", book.getName());
		return new FreemarkerProcessor(cfg).process(map, "book-header.ftl");
	}

	private void copyResources(Book book, File bookRoot) throws IOException {
		// Dependencies (CSS, images, javascripts)
		File includesOrigin = new File(templateDir, "includes/");

		NotFileFilter excludingVersionControlFiles = new NotFileFilter(new NameFileFilter(Arrays.asList("CVS", ".svn")));
		FileUtilities.copyDirectoryToDirectory(includesOrigin, bookRoot, excludingVersionControlFiles);

		//TODO: desperately refactor this...
		Map<String, Integer> indexes = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);

		boolean resourceCopyFailed = false;
		for (Chapter chapter : book.getChapters()) {
			if(!chapter.getResources().isEmpty())
				resourceCopyFailed = copyChaptersResources(bookRoot, chapter, indexes);
		}
		if (resourceCopyFailed) {
			throw new TubainaException("Couldn't copy some resources. See the Logger for further information");
		}
	}
	
	private boolean copyChaptersResources(File bookRoot, Chapter chapter, Map<String, Integer> indexes) {
		File logo = new File(templateDir, "html/logo.png");
		File chapterDirectory = new File(bookRoot, Utilities.toDirectoryName(null, chapter.getTitle()));
		chapterDirectory.mkdir();
		ResourceManipulator manipulator = new HtmlResourceManipulator(chapterDirectory, indexes, logo);
		for (Resource r : chapter.getResources()) {
			try {
				r.copyTo(manipulator);
			} catch (TubainaException e) {
				return true;
			}
		}
		return false;
	}
}