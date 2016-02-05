package br.com.caelum.bibliography;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.bibliography.Bibliography;
import br.com.caelum.tubaina.bibliography.BibliographyEntry;
import br.com.caelum.tubaina.bibliography.BibliographyFactory;
import br.com.caelum.tubaina.bibliography.HtmlBibliographyGenerator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class HtmlBibliographyGeneratorTest {

    private Configuration cfg;
    private HtmlBibliographyGenerator htmlBibGenerator;
    
    @Before
    public void setUp() throws IOException {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "kindle"));
        cfg.setObjectWrapper(new BeansWrapper());
        htmlBibGenerator = new HtmlBibliographyGenerator(cfg);
    }

    @Test
    public void shouldGenerateHtmlBibContent() throws Exception {
        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibsimple.xml"));

        String html = htmlBibGenerator.generateTextOf(bibliography);
        assertTrue(html.contains("Jose da silva"));
        assertTrue(html.contains("Livro legal"));
        assertTrue(html.contains("2012"));
    }
    
    @Test
    public void shouldGenerateHtmlWithJournal() throws Exception {
        BibliographyEntry bibliographyEntry = new BibliographyEntry("autor", "titulo", "2012", "", "article", "ref", "some journal");
        Bibliography bibliography = new Bibliography(Arrays.asList(bibliographyEntry));
        
        String html = htmlBibGenerator.generateTextOf(bibliography);
        assertTrue(html.contains("some journal"));
    }
    
    @Test
    public void shouldNotGenerateHtmlWithEmptyYear() throws Exception {
        BibliographyEntry bibliographyEntry = new BibliographyEntry("autor", "titulo", "", "", "article", "ref", "some journal");
        Bibliography bibliography = new Bibliography(Arrays.asList(bibliographyEntry));
        String html = htmlBibGenerator.generateTextOf(bibliography);
        System.out.println(html);
        assertFalse(html.contains("()"));
    }

    @Test
    public void shouldGenerateHtmlBibContentWithoutIds() throws Exception {
        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibsimplenolabel.xml"));

        String html = htmlBibGenerator.generateTextOf(bibliography);
        System.out.println(html);
        assertTrue(html.contains("Jose da silva"));
        assertTrue(html.contains("Livro legal"));
        assertTrue(html.contains("2012"));
        assertFalse(html.contains("id=\"\""));
    }
}
