package br.com.caelum.tubaina.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RegexTagTest {

    private List<Tag> regexTags;

    @Before
    public void setUp() throws IOException {
        regexTags = new RegexConfigurator().read("/regex.properties", "/latex.properties");
    }

    private String parseWithRegexps(String text) {
        for (Tag tag : regexTags) {
            text = tag.parse(text, "");
        }
        return text;
    }

    @Test
    public void shouldReplaceFancyQuotes() throws Exception {
        String text = "blablabla \"quoted\"";
        text = parseWithRegexps(text);
        assertEquals("blablabla ``quoted''", text);
        
        text = "\"e agora\"!";
        text = parseWithRegexps(text);
        assertEquals("``e agora''!", text);
        
        text = "\'e agora\'!";
        text = parseWithRegexps(text);
        assertEquals("`e agora'!", text);
        
    }
    
    

}
