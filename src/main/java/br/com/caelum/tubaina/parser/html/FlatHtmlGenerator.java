package br.com.caelum.tubaina.parser.html;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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
import br.com.caelum.tubaina.util.FileUtilities;
import br.com.caelum.tubaina.util.Utilities;
import br.com.caelum.tubaina.util.XHTMLValidator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class FlatHtmlGenerator {

	private static final Logger LOG = Logger.getLogger(FlatHtmlGenerator.class);

	private final HtmlParser parser;

	private final boolean shouldValidateXHTML;

	private final File templateDir;

	public FlatHtmlGenerator(final HtmlParser parser, final boolean shouldValidateXHTML, final File templateDir) {
		this.parser = parser;
		this.shouldValidateXHTML = shouldValidateXHTML;
		this.templateDir = templateDir;
	}

	public void generate(final Book b, final File directory) throws IOException {
		// FreeMarker configuration
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(templateDir);
		cfg.setObjectWrapper(new BeansWrapper());

		List<String> dirTree = createDirTree(b, directory);

		StringBuffer sb = new BookToTOC().generateTOC(b, cfg, dirTree);
		File root = saveToFile(new File(directory, dirTree.get(0)), sb);

		int chapterIndex = 1;
		int currentDir = 1;
		for (Chapter c : b.getChapters()) {
			int curdir = currentDir++;
			StringBuffer chHead = new ChapterToString(parser, cfg, dirTree).generateFlatChapterHead(b, c, chapterIndex, curdir);
			StringBuffer chTail = new ChapterToString(parser, cfg, dirTree).generateFlatChapterTail(b, c, chapterIndex, curdir);
			StringBuffer chFullText = new StringBuffer().append(chHead);

			int sectionIndex = 1;
			for (Section s : c.getSections()) {
				if (s.getTitle() != null) { // intro
					sb = new SectionToString(parser, cfg, dirTree).generateFlatSection(b, c.getTitle(), chapterIndex, s,
							sectionIndex, currentDir);
					chFullText.append(sb);
					currentDir++;
					sectionIndex++;
				}
			}
			
			chFullText.append(chTail);
			
			saveToFile(new File(directory, dirTree.get(curdir)), chFullText);

			chapterIndex++;
		}

		if (shouldValidateXHTML) {
			validateXHTML(directory, dirTree);
		}

		copyResources(b, root, dirTree, cfg);
	}

	private List<String> createDirTree(final Book b, final File parent) {
		List<String> dirTree = new ArrayList<String>();

		String rootDir = Utilities.toDirectoryName(null, b.getName());
		File root = new File(parent, rootDir);
		root.mkdir();
		dirTree.add(rootDir);
		for (Chapter c : b.getChapters()) {
			String chapDir = rootDir + "/" + Utilities.toDirectoryName(null, c.getTitle());
			if (dirTree.contains(chapDir)) {
				throw new TubainaException("Doubled archive name: " + c.getTitle());
			}
			File chapter = new File(parent, chapDir);
			chapter.mkdir();
			dirTree.add(chapDir);

			for (Section s : c.getSections()) {
				if (s.getTitle() != null) {
					String secDir = chapDir + "/#" + Utilities.toDirectoryName(null, s.getTitle());
					int equals = 1;
					while (dirTree.contains(secDir)) {
						secDir = secDir.replaceFirst("-" + equals + "$", "");
						secDir += "-" + ++equals;
						LOG.warn("Double section name in the same chapter: " + s.getTitle());
					}
					dirTree.add(secDir);
				}
			}
		}

		return dirTree;
	}

	private File saveToFile(final File directory, final StringBuffer sb) throws IOException {
		File file = new File(directory, "index.html");
		PrintStream ps = new PrintStream(file, "UTF-8");
		ps.print(sb.toString());
		ps.close();
		return directory;
	}

	private void copyResources(final Book b, final File directory, final List<String> dirTree, final Configuration cfg)
			throws IOException {

		boolean resourceCopyFailed = false;

		// Dependencies (CSS, images, javascripts)
		File includes = new File(templateDir, "html/includes/");

		FileUtilities.copyDirectoryToDirectory(includes, directory, new NotFileFilter(new NameFileFilter(new String[] {
				"CVS", ".svn" })));

		Map<String, Integer> indexes = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);

		for (Chapter c : b.getChapters()) {
			File chapdir = new File(directory, Utilities.toDirectoryName(null, c.getTitle()));

			File resources = new File(chapdir, "../resources/");
			resources.mkdir();

			File logo = new File(templateDir, "html/logo.png");
			ResourceManipulator manipulator = new HtmlResourceManipulator(resources, indexes, logo);

			for (Resource r : c.getResources()) {
				try {
					r.copyTo(manipulator);
				} catch (TubainaException e) {
					resourceCopyFailed = true;
				}

			}
		}

		// Creating index
		StringBuffer sb = new IndexToString(dirTree, cfg).createFlatIndex(indexes);
		File file = new File(directory, "index/");
		file.mkdir();
		saveToFile(file, sb);

		if (resourceCopyFailed) {
			throw new TubainaException("Couldn't copy some resources. See the Logger for further information");
		}
	}

	private void validateXHTML(final File directory, final List<String> dirTree) {
		XHTMLValidator validator = new XHTMLValidator();
		boolean foundInvalidXHTML = false;
		for (String s : dirTree) {
			if (!validator.isValid(directory, s)) {
				foundInvalidXHTML = true;
				LOG.warn("This is not a xhtml valid file: " + s + "/index.html");
			}
		}
		if (foundInvalidXHTML) {
			throw new TubainaException("Some xhtml generated is not valid. See " + XHTMLValidator.validatorLogFile
					+ " for further information");
		}

	}
}