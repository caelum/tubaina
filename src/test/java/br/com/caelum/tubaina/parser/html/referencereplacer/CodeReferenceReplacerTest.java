package br.com.caelum.tubaina.parser.html.referencereplacer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import br.com.caelum.tubaina.parser.html.referencereplacer.CodeReferenceReplacer;

public class CodeReferenceReplacerTest {

    @Test
    public void shouldReplaceCodeReferenceWithChapterNumberOnly() throws Exception {
        compareInputAndExpected("codeReferencesTestExpected.html", "codeReferencesTest.html");
    }

    @Test
    public void shouldReplaceCodeReferenceWithChapterNumberOnlyEvenIfTheImageIsOutsideASection()
            throws Exception {
        compareInputAndExpected("codeOutsideSectionReferencesTestExpected.html",
                "codeOutsideSectionReferencesTest.html");
    }

    private void compareInputAndExpected(String expectedFileName, String currentFileName)
            throws FileNotFoundException {
        String htmlContent = new Scanner(new File(
                "src/test/resources/kindle/codeReferenceReplacer/" + currentFileName))
                .useDelimiter("$$").next();
        String htmlContentExpected = new Scanner(new File(
                "src/test/resources/kindle/codeReferenceReplacer/" + expectedFileName))
                .useDelimiter("$$").next();

        String output = new CodeReferenceReplacer().replace(htmlContent);
        assertEquals(htmlContentExpected, output);
    }
}
