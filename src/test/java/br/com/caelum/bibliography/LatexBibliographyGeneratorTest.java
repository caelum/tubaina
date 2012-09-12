package br.com.caelum.bibliography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.bibliography.Bibliography;
import br.com.caelum.tubaina.bibliography.BibliographyEntry;
import br.com.caelum.tubaina.bibliography.BibliographyFactory;
import br.com.caelum.tubaina.bibliography.LatexBibliographyGenerator;

public class LatexBibliographyGeneratorTest {

    private LatexBibliographyGenerator latexBibGenerator;

    @Before
    public void setUp() {
        latexBibGenerator = new LatexBibliographyGenerator();
    }
    
    @Test
    public void shouldGenerateBibContent() throws Exception {
        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibsimple.xml"));

        String expectedBib = new Scanner(new File("src/test/resources/bibliography/book.bib"))
                .useDelimiter("$$").next();

        assertEquals(expectedBib, latexBibGenerator.generateTextOf(bibliography));
    }

    @Test
    public void shouldGenerateBibContentWithPublisher() throws Exception {

        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibwithpublisher.xml"));

        String expectedBib = new Scanner(new File(
                "src/test/resources/bibliography/bookwithpublisher.bib")).useDelimiter("$$").next();

        assertEquals(expectedBib, latexBibGenerator.generateTextOf(bibliography));
    }
    
    @Test
    public void shouldGenerateBibWithoutAField() throws Exception {

        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/miscwithoutyear.xml"));

        String expectedBib = new Scanner(new File(
                "src/test/resources/bibliography/miscwithoutyear.bib")).useDelimiter("$$").next();

        assertEquals(expectedBib, latexBibGenerator.generateTextOf(bibliography));
    }
    
    @Test
    public void shouldGenerateBibliographyWithEmptyHowPublished() throws Exception {
        BibliographyEntry entry = new BibliographyEntry("author", "title", "1999", null, "article", "label", null);
        Bibliography bibliography = new Bibliography(Arrays.asList(entry));
    }
    
    @Test
    public void shouldGenerateBibliographyWithEmptyHowAuthor() throws Exception {
        BibliographyEntry entry = new BibliographyEntry(null, "title", "1999", null, "article", "label", null);
        Bibliography bibliography = new Bibliography(Arrays.asList(entry));
        latexBibGenerator.generateTextOf(bibliography);
    }
    
    @Test
    public void shouldAcceptJournalWhenIsArticle() throws Exception {
        BibliographyEntry entry = new BibliographyEntry("joao", "title", "1999", null, "article", "label", "international conference");
        Bibliography bibliography = new Bibliography(Arrays.asList(entry));
        String bib = latexBibGenerator.generateTextOf(bibliography);
        assertTrue(bib.contains("journal = {international conference}"));
    }
    
    @Test
    public void shouldNotAppearJournalWhenEmpty() throws Exception {
        BibliographyEntry entry = new BibliographyEntry("joao", "title", "1999", null, "article", "label", null);
        Bibliography bibliography = new Bibliography(Arrays.asList(entry));
        String bib = latexBibGenerator.generateTextOf(bibliography);
        assertFalse(bib.contains("journal ="));
    }
    
    
    
    
}
