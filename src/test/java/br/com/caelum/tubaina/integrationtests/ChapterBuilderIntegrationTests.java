package br.com.caelum.tubaina.integrationtests;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaSyntaxErrorsException;
import br.com.caelum.tubaina.builder.ChapterBuilder;

public class ChapterBuilderIntegrationTests {

    @Test(expected = TubainaSyntaxErrorsException.class)
    public void shouldFailWhileBuildingChapterWithOneSection() throws Exception {
        String validCode = "[code]\nvalid code[/code]\n";
        String invalidCode = "[code]\ninvalid code \n[code]\n";
        String invalidList = "[list]\nother invalid code \n[list]\n";
        new ChapterBuilder("Should fail", validCode, "[section Lala]" + validCode + 
                invalidCode + invalidList).build();
    }
    
    @Test(expected = TubainaSyntaxErrorsException.class)
    public void shouldFailWhileBuildingChapterWithTwoSectionWithMultipleErrors() throws Exception {
        String validCode = "[code]\nvalid code[/code]\n";
        String invalidCode = "[code]\ninvalid code \n[code]\n";
        String invalidList = "[list]\nother invalid code \n[list]\n";
        String invalidSection1 = "[section section1]" + invalidCode; 
        String invalidSection2 = "[section section2]" + invalidList; 
        new ChapterBuilder("Should fail", validCode, invalidSection1 + invalidSection2).build();
    }
}
