package br.com.caelum.tubaina.parser.html.kindle;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class ChapterToStringTest {

    private ChapterToString chapterToString;

    @Before
    public void setUp() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "kindle"));
        cfg.setObjectWrapper(new BeansWrapper());

        Parser parser = new HtmlParser(new RegexConfigurator().read("/regex.properties",
                "/kindle.properties"), false);
        ArrayList<String> dirTree = new ArrayList<String>();

        chapterToString = new ChapterToString(parser, cfg, dirTree);
    }

    private Chapter createChapter(String title, String introduction) {
        return new ChapterBuilder(title, introduction, "").build();
    }

    private int countOccurrences(String text, String substring) {
        String[] tokens = text.split(substring);
        return tokens.length - 1;
    }

    @Test
    public void testGenerateChapterWithSections() {
        Chapter chapter = createChapter("titulo", "introducao");

        Book book = new BookBuilder("meu-livro").build();
        String generatedContent = chapterToString.generateKindleHtmlChapter(book, chapter,
                new StringBuffer("<p>section content</p>")).toString();
        assertEquals(1, countOccurrences(generatedContent, "<div class='referenceable'>"));
        assertEquals(1, countOccurrences(generatedContent, "<p>section content</p>"));
    }

}
