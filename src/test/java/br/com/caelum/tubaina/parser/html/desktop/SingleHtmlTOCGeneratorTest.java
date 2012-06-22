package br.com.caelum.tubaina.parser.html.desktop;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SingleHtmlTOCGeneratorTest {
    
    @Test
    public void shouldCreateATOCFromBook() throws Exception {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "singlehtml/"));
        cfg.setObjectWrapper(new BeansWrapper());
        BookBuilder builder = new BookBuilder("title");

        builder.addAllReaders(Arrays.asList((Reader) new StringReader(
                "[chapter     O que é java?   ]\n" + "texto da seção\n"
                        + "[section Primeira seção]\n" + "texto da prim seção\n"
                        + "[section Segunda seção]\n" + "texto da segunda seção\n\n")), new ArrayList<Reader>());

        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter Introdução]\n"
                + "Algum texto de introdução\n")), new ArrayList<Reader>());
        
        SingleHtmlTOCGenerator tocGenerator = new SingleHtmlTOCGenerator(builder.build(), cfg);
        
        String toc = tocGenerator.generateTOC().toString();
        
        System.out.println(toc);
        
        assertTrue(toc.contains("O que é java"));
        assertTrue(toc.contains("Primeira seção"));
        assertTrue(toc.contains("Segunda seção"));
        
    }

}
