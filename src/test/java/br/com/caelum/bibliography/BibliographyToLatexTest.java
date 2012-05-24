package br.com.caelum.bibliography;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

public class BibliographyToLatexTest {

    @Test
    public void shouldGenerateBibContent() throws Exception {
        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibsimple.xml"));
        BibliographyToLatex latexBibGenerator = new BibliographyToLatex(bibliography);

        String expectedBib = new Scanner(new File("src/test/resources/bibliography/book.bib"))
                .useDelimiter("$$").next();

        assertEquals(expectedBib, latexBibGenerator.generate());
    }

    @Test
    public void shouldGenerateBibContentWithPublisher() throws Exception {

        Bibliography bibliography = new BibliographyFactory().build(new File(
                "src/test/resources/bibliography/bibwithpublisher.xml"));
        BibliographyToLatex latexBibGenerator = new BibliographyToLatex(bibliography);

        String expectedBib = new Scanner(new File(
                "src/test/resources/bibliography/bookwithpublisher.bib")).useDelimiter("$$").next();

        assertEquals(expectedBib, latexBibGenerator.generate());
    }
}
