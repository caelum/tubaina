package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlChapterGeneratorTest {
    
    private Chapter createChapter(final String introduction, final String chapterText) {
        return new ChapterBuilder("Title", introduction, chapterText, 1).build();
    }
    
    @Test
    public void shouldGenerateChapterInsideADiv() throws Exception {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "singlehtml/"));
        cfg.setObjectWrapper(new BeansWrapper());

        Parser parser = new HtmlParser(new RegexConfigurator().read("/regex.properties", "/html.properties"), false);

        ArrayList<String> dirTree = new ArrayList<String>();
        dirTree.add("livro");
        dirTree.add("livro/01-capitulo");
        dirTree.add("livro/01-capitulo/01-primeira");
        dirTree.add("livro/01-capitulo/02-segunda");

        SingleHtmlChapterGenerator singleHtmlChapterGenerator = new SingleHtmlChapterGenerator(parser, cfg);
        
        Chapter c = createChapter("introducao", "[section primeira] conteudo da primeira "
                + "\n[section segunda] conteudo da segunda");
        
        String string = singleHtmlChapterGenerator.generateSingleHtmlChapter(new BookBuilder("some name").build(), c).toString();
        
        Assert.assertEquals(1, countOccurrences(string, "<div class=\"chapter referenceable\">"));
    }
    
    private int countOccurrences(final String text, final String substring) {
        String[] tokens = text.split(substring);
        return tokens.length - 1;
    }
}
