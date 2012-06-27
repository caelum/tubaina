package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class BookToTOCTest {

    private Configuration cfg;

    private String sectionIdentifier;

    private String chapterIdentifier;

    @Before
    public void setUp() throws IOException {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "html/"));
        cfg.setObjectWrapper(new BeansWrapper());

        chapterIdentifier = "class=\"indexChapter\"";
        sectionIdentifier = "class=\"indexSection\"";
    }

    @Test
    public void testGenerateBookWithoutSections() {
        BookBuilder builder = new BookBuilder("Title");
        builder.addAllReaders(Arrays.asList((Reader) new StringReader(
                "[chapter primeiro] um conteúdo"), (Reader) new StringReader(
                "[chapter segundo] um conteúdo")), new ArrayList<Reader>());
        Book b = builder.build();
        BookToTOC generator = new BookToTOC();
        List<String> dirTree = new ArrayList<String>();
        dirTree.add("livro");
        dirTree.add("livro/01-primeiro");
        dirTree.add("livro/02-segundo");

        String toc = generator.generateTOC(b, cfg, dirTree).toString();

        Assert.assertEquals(0, countOccurrences(toc, sectionIdentifier));
        Assert.assertEquals(2, countOccurrences(toc, chapterIdentifier));
    }

    @Test
    public void testGenerateBookWithSections() {
        BookBuilder builder = new BookBuilder("Title");
        builder.addAllReaders(Arrays.asList((Reader) new StringReader(
                "[chapter unico] um conteúdo \n" +
                "[section uma] lalala \n" +
                "[section duas] lalala \n")), new ArrayList<Reader>());
        Book b = builder.build();
        BookToTOC generator = new BookToTOC();
        

        List<String> dirTree = new ArrayList<String>();
        dirTree.add("livro");
        dirTree.add("livro/01-unico");
        dirTree.add("livro/01-unico/01-uma");
        dirTree.add("livro/01-unico/02-duas");

        String toc = generator.generateTOC(b, cfg, dirTree).toString();
        System.out.println(toc);
        
        Assert.assertEquals(2, countOccurrences(toc, sectionIdentifier));
        Assert.assertEquals(1, countOccurrences(toc, chapterIdentifier));
        Assert.assertEquals(1, countOccurrences(toc, "href=\"unico/index.html#1-1-uma\""));
        Assert.assertEquals(1, countOccurrences(toc, "href=\"unico/index.html#1-2-duas\""));
    }

    private Book createBook(final String bookText) {
        BookBuilder builder = new BookBuilder("Title");
        builder.addAllReaders(Arrays.asList((Reader) new StringReader(bookText)),
                new ArrayList<Reader>());
        Book b = builder.build();
        return b;
    }

    private int countOccurrences(final String text, final String substring) {
        String[] tokens = text.split(substring);
        return tokens.length - 1;
    }
}
