package br.com.caelum.bibliography;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.bibliography.Bibliography;
import br.com.caelum.tubaina.bibliography.BibliographyEntry;
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

        String html = htmlBibGenerator.generateTextOf(bibliography);
        assertTrue(html.contains("Jose da silva"));
        assertTrue(html.contains("Livro legal"));
        assertTrue(html.contains("2012"));
    }
    
    @Test
    public void shouldGenerateHtmlWithJournal() throws Exception {
        BibliographyEntry bibliographyEntry = new BibliographyEntry("autor", "titulo", "2012", "", "article", "ref", "some journal");
        Bibliography bibliography = new Bibliography(Arrays.asList(bibliographyEntry));
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "kindle"));
        cfg.setObjectWrapper(new BeansWrapper());
        HtmlBibliographyGenerator htmlBibGenerator = new HtmlBibliographyGenerator(cfg);
        
        String html = htmlBibGenerator.generateTextOf(bibliography);
        assertTrue(html.contains("some journal"));
    }
    
}
