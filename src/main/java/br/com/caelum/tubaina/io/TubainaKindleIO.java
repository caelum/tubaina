package br.com.caelum.tubaina.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.util.FileUtilities;

public class TubainaKindleIO {

    private final File templateDir;
    private final ResourceManipulatorFactory resourceManipulatorFactory;

    public TubainaKindleIO(File templateDir, ResourceManipulatorFactory resourceManipulatorFactory) {
        this.templateDir = templateDir;
        this.resourceManipulatorFactory = resourceManipulatorFactory;
    }

    public TubainaHtmlDir createTubainaDir(File bookRoot, Book book) {
        try {
            File templateIncludes = new File(templateDir, "includes/");
            if (!templateIncludes.exists()) throw new TubainaException("why?");
            NotFileFilter excludingVersionControlFiles = new NotFileFilter(new NameFileFilter(Arrays.asList("CVS", ".svn", ".git")));
            FileUtilities.copyDirectoryToDirectory(templateIncludes, bookRoot, excludingVersionControlFiles);
        } catch (IOException e) {
            throw new TubainaException("Error while copying template files", e);
        }
        
        return new TubainaHtmlDir(bookRoot, templateDir, resourceManipulatorFactory);
    }
    
}