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
	private final ResourceManipulatorFactory resourceManipulatorFactory;

	public TubainaHtmlIO(File templateDir, ResourceManipulatorFactory resourceManipulatorFactory) {
		this.templateDir = templateDir;
		this.resourceManipulatorFactory = resourceManipulatorFactory;
	}

	public TubainaHtmlDir createTubainaDir(File  outputFolder, Book book) {
		String bookName = Utilities.toDirectoryName(null, book.getName());
		File bookRoot = new File(outputFolder, bookName);
		try {
			File templateIncludes = new File(templateDir, "includes/");
			if (!templateIncludes.exists()) {
			    throw new TubainaException("Could not find includes dir at: " + templateIncludes.getAbsolutePath() + ".");
			}
			NotFileFilter excludingVersionControlFiles = new NotFileFilter(new NameFileFilter(Arrays.asList("CVS", ".svn", ".git")));
			FileUtilities.copyDirectoryToDirectory(templateIncludes, bookRoot, excludingVersionControlFiles);
		} catch (IOException e) {
			throw new TubainaException("Error while copying template files", e);
		}
		
		return new TubainaHtmlDir(bookRoot, templateDir, resourceManipulatorFactory);
	}
	
}