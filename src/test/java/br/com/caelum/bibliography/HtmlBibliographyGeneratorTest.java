package br.com.caelum.bibliography;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.bibliography.Bibliography;
import br.com.caelum.tubaina.bibliography.BibliographyFactory;
import br.com.caelum.tubaina.bibliography.HtmlBibliographyGenerator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class HtmlBibliographyGeneratorTest {

    @Test
    public void shouldGenerateHtmlBibContent() throws Exception {
        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibsimple.xml"));
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "kindle"));
        cfg.setObjectWrapper(new BeansWrapper());
        HtmlBibliographyGenerator htmlBibGenerator = new HtmlBibliographyGenerator(cfg);

        String expectedBib = new Scanner(new File("src/test/resources/bibliography/bib.html"))
                .useDelimiter("$$").next();

        assertEquals(expectedBib, htmlBibGenerator.generateTextOf(bibliography));
    }
    
}
