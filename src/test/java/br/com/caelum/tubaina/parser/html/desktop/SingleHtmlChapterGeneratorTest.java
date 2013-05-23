package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlChapterGeneratorTest {
    
    private Chapter createChapter(final String introduction, final String chapterText) {
        return new ChapterBuilder("Title", introduction, chapterText, 0, new SectionsManager()).build();
    }
    
    @Test
    public void shouldGenerateChapterInsideADiv() throws Exception {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "singlehtml/"));
        cfg.setObjectWrapper(new BeansWrapper());

        Parser parser = new HtmlParser(new RegexConfigurator().read("/regex.properties", "/html.properties"));

        ArrayList<String> dirTree = new ArrayList<String>();
        dirTree.add("livro");
        dirTree.add("livro/01-capitulo");
        dirTree.add("livro/01-capitulo/01-primeira");
        dirTree.add("livro/01-capitulo/02-segunda");

        SingleHtmlChapterGenerator singleHtmlChapterGenerator = new SingleHtmlChapterGenerator(parser, cfg, new ArrayList<String>());
        
        Chapter c = createChapter("introducao", "[section primeira] conteudo da primeira "
                + "\n[section segunda] conteudo da segunda");
        
        Book book = new BookBuilder("some name", new SectionsManager()).build();
        new HtmlModule().inject(book);
        new HtmlModule().inject(c);
        
		String string = singleHtmlChapterGenerator.generateSingleHtmlChapter(book, c).toString();
        
        Assert.assertEquals(1, countOccurrences(string, "<div class=\"chapter referenceable\">"));
    }
    
    private int countOccurrences(final String text, final String substring) {
        String[] tokens = text.split(substring);
        return tokens.length - 1;
    }
}
