package br.com.caelum.tubaina.parser.html.kindle.referencereplacer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import br.com.caelum.tubaina.parser.html.referencereplacer.ImageReferenceReplacer;

public class ImageReferenceReplacerTest {

    @Test
    public void shouldReplaceImageReferenceWithChapterNumberOnly() throws Exception {
        compareInputAndExpected("imageReferencesTestExpected.html", "imageReferencesTest.html");
    }

    @Test
    public void shouldReplaceImageReferenceWithChapterNumberOnlyEvenIfTheImageIsOutsideASection()
            throws Exception {
        compareInputAndExpected("imageOutsideSectionReferencesTestExpected.html",
                "imageOutsideSectionReferencesTest.html");
    }

    private void compareInputAndExpected(String expectedFileName, String currentFileName)
            throws FileNotFoundException {
        String htmlContent = new Scanner(new File(
                "src/test/resources/kindle/imageReferenceReplacer/" + currentFileName))
                .useDelimiter("$$").next();
        String htmlContentExpected = new Scanner(new File(
                "src/test/resources/kindle/imageReferenceReplacer/" + expectedFileName))
                .useDelimiter("$$").next();

        String output = new ImageReferenceReplacer().replace(htmlContent);
        assertEquals(htmlContentExpected, output);
    }
}
