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
    
    @Test
    public void shouldReplaceCommonUrlForLink() throws Exception {
        String text = "some text and a link: http://caelum.com.br more text";
        text = parseWithRegexps(text);
        assertEquals("some text and a link: \\link{http://caelum.com.br} more text", text);
        
        text = "some text and a https link: https://caelum.com.br more text";
        text = parseWithRegexps(text);
        assertEquals("some text and a https link: \\link{https://caelum.com.br} more text", text);
    }
    
    @Test
    public void shouldNotConsiderPunctuationSymbolsAtTheEndOfUrlForLink() throws Exception {
        testHttpAndHttps("This is a link ", "caelum.com.br", ".");
        testHttpAndHttps("This is a link ", "caelum.com.br", ";");
        testHttpAndHttps("This is a link ", "caelum.com.br", ":");
        testHttpAndHttps("This is a link ", "caelum.com.br", ";");
    }
    
    private void testHttpAndHttps(String textBefore, String link, String textAfter) {
        String http = textBefore + "http://" +  link + textAfter;
        String text = parseWithRegexps(http);
        assertEquals(textBefore + "\\link{http://" + link + "}" + textAfter, text);
        
        String https = textBefore + "https://" +  link + textAfter;
        text = parseWithRegexps(https);
        assertEquals(textBefore + "\\link{https://" + link + "}" + textAfter, text);
        
        
    }
}
