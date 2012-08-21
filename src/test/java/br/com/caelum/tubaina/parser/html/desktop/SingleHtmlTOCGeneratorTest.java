package br.com.caelum.tubaina.parser.html.desktop;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlTOCGeneratorTest {
    
    private Configuration cfg;

    @Before
    public void setUp() throws IOException {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR,
                "singlehtml/"));
        cfg.setObjectWrapper(new BeansWrapper());
    }

    @Test
    public void shouldCreateATOCFromBook() throws Exception {
        setUp();
        Book build = createBook();
        
        SingleHtmlTOCGenerator tocGenerator = new SingleHtmlTOCGenerator(build, cfg);
        String toc = tocGenerator.generateTOC().toString();
        
        assertTrue(toc.contains("O que é java"));
        assertTrue(toc.contains("Primeira seção"));
        assertTrue(toc.contains("Segunda seção"));
    }


    private Book createBook() {
        BookBuilder builder = new BookBuilder("title");
        builder.addReaderFromString(
                "[chapter     O que é java?   ]\n" + "texto da seção\n"
                        + "[section Primeira seção]\n" + "texto da prim seção\n"
                        + "[section Segunda seção]\n" + "texto da segunda seção\n\n");
        builder.addReaderFromString("[chapter Introdução]\n"
                + "Algum texto de introdução\n");
        Book build = builder.build();
        return build;
    }

}
