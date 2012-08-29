package br.com.caelum.tubaina.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class LatexRegexTagTest extends RegexTagTest{

    @Before
    public void setUp() throws IOException {
        regexTags = new RegexConfigurator().read("/regex.properties", "/latex.properties");
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
    
    @Test
    public void shouldReplaceLinkWhenItsBeforeEndOfLine() throws Exception {
        String text = "Então visite http://www.xcubelabs.com/the-android-story.php e http://www.theverge.com/2011/12/7/2585779/android-history\n";
        text = parseWithRegexps(text);
        assertEquals("Então visite \\link{http://www.xcubelabs.com/the-android-story.php} e \\link{http://www.theverge.com/2011/12/7/2585779/android-history}\n", text);
    }
    
    @Test
    public void testLinkWithMultipleBracesInsideBug() {
        String text = "http://farm{farm-id}.static.flickr.com/{server-id}/{id}\\_{secret}.jpg";
        String expected = "\\link{http://farm{farm-id}.static.flickr.com/{server-id}/{id}\\_{secret}.jpg}";
        text = parseWithRegexps(text);
        assertEquals(expected, text);
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
