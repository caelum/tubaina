package br.com.caelum.tubaina.parser.html;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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
		this.templateDir = templateDir;
	}

	public void generate(Book book, File directory) throws IOException {
		// FreeMarker configuration
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(templateDir);
		cfg.setObjectWrapper(new BeansWrapper());
		
		File bookRoot = new File(directory, Utilities.toDirectoryName(null, book.getName()));
		bookRoot.mkdir();
		
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
		bookContent.append(new FreemarkerProcessor(cfg).process(new HashMap<String, Object>(), "singlehtml/book-footer.ftl"));
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
		return new FreemarkerProcessor(cfg).process(map, "singlehtml/book-header.ftl");
		}

	private void copyResources(Book book, File bookRoot)
			throws IOException {

		boolean resourceCopyFailed = false;

		// Dependencies (CSS, images, javascripts)
		File includes = new File(templateDir, "singlehtml/includes/");

		FileUtilities.copyDirectoryToDirectory(includes, bookRoot, new NotFileFilter(new NameFileFilter(new String[] {
				"CVS", ".svn" })));

		Map<String, Integer> indexes = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);

		for (Chapter c : book.getChapters()) {
			File chapterDirectory = new File(bookRoot, Utilities.toDirectoryName(null, c.getTitle()));
			chapterDirectory.mkdir();

			File logo = new File(templateDir, "html/logo.png");
			ResourceManipulator manipulator = new HtmlResourceManipulator(chapterDirectory, indexes, logo);

			for (Resource r : c.getResources()) {
				try {
					r.copyTo(manipulator);
				} catch (TubainaException e) {
					resourceCopyFailed = true;
				}
			}
		}

		if (resourceCopyFailed) {
			throw new TubainaException("Couldn't copy some resources. See the Logger for further information");
		}
	}
}