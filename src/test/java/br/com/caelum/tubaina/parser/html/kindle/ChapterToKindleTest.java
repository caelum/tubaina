package br.com.caelum.tubaina.parser.html.kindle;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class ChapterToKindleTest {

    private ChapterToKindle chapterToString;

    @Before
    public void setUp() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "kindle"));
        cfg.setObjectWrapper(new BeansWrapper());

        Parser parser = new HtmlParser(new RegexConfigurator().read("/regex.properties",
                "/kindle.properties"));
        chapterToString = new ChapterToKindle(parser, cfg);
    }

    private Chapter createChapter(String title, String introduction, String content) {
        return new ChapterBuilder(title, introduction, content, 1).build();
    }

    private int countOccurrences(String text, String substring) {
        String[] tokens = text.split(substring);
        return tokens.length - 1;
    }

    @Test
    public void testGenerateChapterWithSections() {
        Chapter chapter = createChapter("chapter title", "introduction",
                "[section section one] section content");

        String generatedContent = chapterToString.generateKindleHtmlChapter(chapter).toString();
        assertEquals(2, countOccurrences(generatedContent, "<div class='referenceable'>"));
        assertEquals(1, countOccurrences(generatedContent, "<h2.*>\\d+ - chapter title</h2>"));
        assertEquals(1, countOccurrences(generatedContent, "<h3.*>\\W*\\d+\\.1 - section one\\W*</h3>"));
    }
}
