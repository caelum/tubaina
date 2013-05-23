package br.com.caelum.tubaina.parser.html.desktop;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class BookToTOCTest {

    private Configuration cfg;

    private String sectionIdentifier;

    private String chapterIdentifier;

	private BookBuilder builder;

    @Before
    public void setUp() throws IOException {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "html/"));
        cfg.setObjectWrapper(new BeansWrapper());

        chapterIdentifier = "class=\"indexChapter\"";
        sectionIdentifier = "class=\"indexSection\"";
        builder = new BookBuilder("Title", new SectionsManager());
    }

    @Test
    public void testGenerateBookWithoutSections() {
        List<String> chapters = Arrays.asList("[chapter primeiro] um conteúdo", "[chapter segundo] um conteúdo");
        builder.addReaderFromStrings(chapters);
        Book b = builder.build();
        BookToTOC generator = new BookToTOC();
        List<String> dirTree = new ArrayList<String>();
        dirTree.add("livro");
        dirTree.add("livro/01-primeiro");
        dirTree.add("livro/02-segundo");

        String toc = generator.generateTOC(b, cfg, dirTree).toString();

        assertEquals(0, countOccurrences(toc, sectionIdentifier));
        assertEquals(2, countOccurrences(toc, chapterIdentifier));
    }

    @Test
    public void testGenerateBookWithSections() {
        builder.addReaderFromString("[chapter unico] um conteúdo \n" +
                "[section uma] lalala \n" +
                "[section duas] lalala \n");
        Book b = builder.build();
        BookToTOC generator = new BookToTOC();
        

        List<String> dirTree = new ArrayList<String>();
        dirTree.add("livro");
        dirTree.add("livro/01-unico");
        dirTree.add("livro/01-unico/01-uma");
        dirTree.add("livro/01-unico/02-duas");

        String toc = generator.generateTOC(b, cfg, dirTree).toString();
        
        assertEquals(2, countOccurrences(toc, sectionIdentifier));
        assertEquals(1, countOccurrences(toc, chapterIdentifier));
        assertEquals(1, countOccurrences(toc, "href=\"unico/index.html#1-1-uma\""));
        assertEquals(1, countOccurrences(toc, "href=\"unico/index.html#1-2-duas\""));
    }

    private int countOccurrences(final String text, final String substring) {
        String[] tokens = text.split(substring);
        return tokens.length - 1;
    }
}
