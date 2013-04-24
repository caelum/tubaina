package br.com.caelum.tubaina.parser.html.referencereplacer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class BibliographyReferenceReplacerTest {

    @Test
    public void shouldReplaceCodeReferenceWithChapterNumberOnly() throws Exception {
        compareInputAndExpected("bibExpected.html", "bibTest.html");
    }

    private void compareInputAndExpected(String expectedFileName, String currentFileName)
            throws FileNotFoundException {
        String htmlContent = new Scanner(new File(
                "src/test/resources/kindle/bibliographyReferenceReplacer/" + currentFileName))
                .useDelimiter("$$").next();
        String htmlContentExpected = new Scanner(new File(
                "src/test/resources/kindle/bibliographyReferenceReplacer/" + expectedFileName))
                .useDelimiter("$$").next();

        String output = new BibliographyReferenceReplacer().replace(htmlContent);
        assertEquals(htmlContentExpected, output);
    }
}
