package br.com.caelum.tubaina.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.util.FileUtilities;
import br.com.caelum.tubaina.util.Utilities;

public class TubainaHtmlIO {

	private final File templateDir;

	public TubainaHtmlIO(File templateDir) {
		this.templateDir = templateDir;
	}

	public TubainaDir createTubainaDir(File  outputFolder, Book book) {
		String bookName = Utilities.toDirectoryName(null, book.getName());
		File bookRoot = new File(outputFolder, bookName);
		try {
			File templateIncludes = new File(templateDir, "includes/");
			if (!templateIncludes.exists()) throw new TubainaException("why?");
			NotFileFilter excludingVersionControlFiles = new NotFileFilter(new NameFileFilter(Arrays.asList("CVS", ".svn", ".git")));
			FileUtilities.copyDirectoryToDirectory(templateIncludes, bookRoot, excludingVersionControlFiles);
		} catch (IOException e) {
			throw new TubainaException("Error while copying template files", e);
		}

		return new TubainaDir(bookRoot, templateDir);
	}
	
}