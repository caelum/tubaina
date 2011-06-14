package br.com.caelum.tubaina.parser.html;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.resources.HtmlResourceManipulator;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceManipulator;
import br.com.caelum.tubaina.util.FileUtilities;
import br.com.caelum.tubaina.util.Utilities;

public class TubainaIO {

	private final File templateDir;

	public TubainaIO(File templateDir) {
		this.templateDir = templateDir;
	}

	public void saveFilesForThis(Book book, File outputFolder, StringBuffer bookContent) throws IOException {
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
	
	private void copyResources(Book book, File bookRoot) throws IOException {
		File templateIncludes = new File(templateDir, "includes/");
		NotFileFilter excludingVersionControlFiles = new NotFileFilter(new NameFileFilter(Arrays.asList("CVS", ".svn", ".git")));
		FileUtilities.copyDirectoryToDirectory(templateIncludes, bookRoot, excludingVersionControlFiles);

		//TODO: desperately refactor this...
		Map<String, Integer> indexes = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
		
		File resourcesDirectory = new File(bookRoot.getParent(), "resources/");
		resourcesDirectory.mkdir();
		
		boolean resourceCopyFailed = false;
		for (Chapter chapter : book.getChapters()) {
			if(!chapter.getResources().isEmpty())
				resourceCopyFailed = copyChaptersResources(resourcesDirectory, chapter, indexes);
		}

		if (resourceCopyFailed) {
			throw new TubainaException("Couldn't copy some resources. See the Logger for further information");
		}
	}
	
	private boolean copyChaptersResources(File resourcesDirectory, Chapter chapter, Map<String, Integer> indexes) {
		//TODO: remove this asap too
		File logo = new File(templateDir, "logo.png");
		
		ResourceManipulator manipulator = new HtmlResourceManipulator(resourcesDirectory , indexes, logo);
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