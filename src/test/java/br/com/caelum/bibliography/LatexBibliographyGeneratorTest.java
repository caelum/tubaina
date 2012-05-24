package br.com.caelum.bibliography;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

public class LatexBibliographyGeneratorTest {

    @Test
    public void shouldGenerateBibContent() throws Exception {
        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibsimple.xml"));
        LatexBibliographyGenerator latexBibGenerator = new LatexBibliographyGenerator();

        String expectedBib = new Scanner(new File("src/test/resources/bibliography/book.bib"))
                .useDelimiter("$$").next();

        assertEquals(expectedBib, latexBibGenerator.generateTextOf(bibliography));
    }

    @Test
    public void shouldGenerateBibContentWithPublisher() throws Exception {

        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibwithpublisher.xml"));
        LatexBibliographyGenerator latexBibGenerator = new LatexBibliographyGenerator();

        String expectedBib = new Scanner(new File(
                "src/test/resources/bibliography/bookwithpublisher.bib")).useDelimiter("$$").next();

        assertEquals(expectedBib, latexBibGenerator.generateTextOf(bibliography));
    }
}
