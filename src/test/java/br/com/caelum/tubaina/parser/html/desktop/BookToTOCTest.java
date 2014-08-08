package br.com.caelum.tubaina.parser.html.desktop;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.MockedModule;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.RegexTag;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class BookToTOCTest {

    private Configuration cfg;

    private String sectionIdentifier;
    
    private String chapterIdentifier;
    
    private String introChapterIdentifier;
    
    private String introSectionIdentifier;

	private BookBuilder builder;

	private MockedModule module;

	private Parser parser;

    @Before
    public void setUp() throws IOException {
    	module = new MockedModule();
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "html/"));
        cfg.setObjectWrapper(new BeansWrapper());
        
		RegexConfigurator configurator = new RegexConfigurator();
		List<RegexTag> tags = configurator.read("/regex.properties", "/html.properties");
		this.parser = new HtmlParser(tags);

        chapterIdentifier = "class=\"indexChapter\"";
        sectionIdentifier = "class=\"indexSection\"";
        introChapterIdentifier = "class=\"chapter";
        introSectionIdentifier = "class=\"section";
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

        String toc = generator.generateTOC(b, cfg, dirTree, parser).toString();

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

        String toc = generator.generateTOC(b, cfg, dirTree, parser).toString();
        
        assertEquals(2, countOccurrences(toc, sectionIdentifier));
        assertEquals(1, countOccurrences(toc, chapterIdentifier));
        assertEquals(1, countOccurrences(toc, "href=\"unico/index.html#1-1-uma\""));
        assertEquals(1, countOccurrences(toc, "href=\"unico/index.html#1-2-duas\""));
    }
    
    @Test
    public void shouldGenerateBookWithIntroduction() {
        List<String> agradecimentos = Arrays.asList("[chapter agradecimentos]\n" +  "um agradecimento\n");
        List<String> prefacio = Arrays.asList("[chapter prefacio]\n" +  "um prefacio\n");
        builder.addAllReadersOfNonNumberedFromStrings(agradecimentos);
        builder.addAllReadersOfNonNumberedFromStrings(prefacio);
        Book b = builder.build();
        module.inject(b);
        BookToTOC generator = new BookToTOC();
        List<String> dirTree = new ArrayList<String>();
        dirTree.add("livro");
        String toc = generator.generateTOC(b, cfg, dirTree, null).toString();
        assertEquals(2, countOccurrences(toc, introChapterIdentifier));
        assertEquals(0, countOccurrences(toc, introSectionIdentifier));
    }
    
    @Test
    public void shouldParseMarkUpsInsideTOC() {
        List<String> agradecimentos = Arrays.asList("[chapter agradecimentos]\n" +  "um agradecimento\n");
        List<String> prefacio = Arrays.asList("[chapter prefacio]\n" +  "um prefacio\n");
        List<String> chapters = Arrays.asList("[chapter ::primeiro::] um conteúdo", "[chapter **segundo**] um conteúdo");
        builder.addAllReadersOfNonNumberedFromStrings(agradecimentos);
        builder.addAllReadersOfNonNumberedFromStrings(prefacio);
        builder.addReaderFromStrings(chapters);
        
        Book b = builder.build();
        module.inject(b);
        BookToTOC generator = new BookToTOC();
        
        List<String> dirTree = new ArrayList<String>();
        dirTree.add("livro");
        dirTree.add("livro/primeiro");
        dirTree.add("livro/primeiro");
        dirTree.add("livro/segundo");

        String toc = generator.generateTOC(b, cfg, dirTree, parser).toString();
        System.out.println(toc);
        
        Assert.assertFalse(toc.contains("::"));
        Assert.assertFalse(toc.contains("**"));
    }

    private int countOccurrences(final String text, final String substring) {
        String[] tokens = text.split(substring);
        return tokens.length - 1;
    }
}
