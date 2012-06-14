package br.com.caelum.tubaina.parser.html.referencereplacer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class SingleHtmlChapterReferenceReplacerTest {

    @Test
    public void shouldReplaceCodeReferenceWithChapterNumberOnly() throws Exception {
        compareInputAndExpected("chapterRefTestExpected.html", "chapterRefTest.html");
    }

    private void compareInputAndExpected(String expectedFileName, String currentFileName)
            throws FileNotFoundException {
        String htmlContent = new Scanner(new File(
                "src/test/resources/singlehtml/referenceReplacer/" + currentFileName))
                .useDelimiter("$$").next();
        String htmlContentExpected = new Scanner(new File(
                "src/test/resources/singlehtml/referenceReplacer/" + expectedFileName))
                .useDelimiter("$$").next();

        String output = new SingleHtmlChapterReferenceReplacer().replace(htmlContent);
        assertEquals(htmlContentExpected, output);
    }
}
