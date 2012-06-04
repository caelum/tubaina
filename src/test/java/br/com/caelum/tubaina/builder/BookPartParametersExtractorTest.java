package br.com.caelum.tubaina.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BookPartParametersExtractorTest {
    
    private BookPartParametersExtractor extractor;

    @Before
    public void setUp() {
        extractor = new BookPartParametersExtractor();
    }

    @Test
    public void shouldExtractTitle() throws Exception {
        String content = "[part \"parte um\" illustration=resources/image.png]\n" + "[chapter capitulo um]\n"
                + "introducao do capitulo um\n" + "[section secao um]\n" + "conteudo da secao um";
        assertEquals("parte um", extractor.extractTitleFrom(content));
    }
    
    @Test
    public void shouldFindPart() throws Exception {
        String content = "[part \"parte um\" illustration=resources/image.png]\n" + "[chapter capitulo um]\n"
                + "introducao do capitulo um\n" + "[section secao um]\n" + "conteudo da secao um";
        assertTrue(extractor.containsPartTag(content));
    }
    
    @Test
    public void shouldNotFindPart() throws Exception {
        String content = "[chapter capitulo um]\n"
                + "introducao do capitulo um\n" + "[section secao um]\n" + "conteudo da secao um";
        assertFalse(extractor.containsPartTag(content));
    }

}
