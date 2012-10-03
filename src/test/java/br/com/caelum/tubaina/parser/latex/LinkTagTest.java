package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkTagTest {

    private LinkTag linkTag;
    
    @Before
    public void setUp() {
        this.linkTag = new LinkTag("\\\\href{$1}{$1}$2");
    }
    
    @Test
    public void testLinkWithHttpTagInline() {
        testLink("http://www.caelum.com.br/");
    }
    
    @Test
    public void testLinkWithHttpsTagInline() {
        testLink("https://www.caelum.com.br/");
    }
    
    @Test
    public void testLinkWithParentheses() {
        String result = linkTag.parse("(http://www.caelum.com.br/)", null);
        Assert.assertEquals("(\\href{http://www.caelum.com.br/}{http://www.caelum.com.br/})", result);
    }

    @Test
    public void shouldReplaceTwoLinks() {
        String content = "http://caelum.com.br/ rest of the text http://caelum.com.br/ another";
        String result = linkTag.parse(content, null);
        assertEquals("\\href{http://caelum.com.br/}{http://caelum.com.br/} " +
        		"rest of the text \\href{http://caelum.com.br/}" +
        		"{http://caelum.com.br/} another", result);
    }
    
    @Test
    public void testLinkWithBracesInsideBug() {
        testLink("http://localhost/{id}");
    }
    
    @Test
    public void testLinkWithMultipleBracesInsideBug() {
        testLink("http://farm{farm-id}.static.flickr.com/{server-id}/{id}_{secret}.jpg");
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
        text = linkTag.parse(text, null);
        assertEquals("Então visite \\href{http://www.xcubelabs.com/the-android-story.php}{http://www.xcubelabs.com/the-android-story.php} e \\href{http://www.theverge.com/2011/12/7/2585779/android-history}{http://www.theverge.com/2011/12/7/2585779/android-history}\n", text);
    }

    private void testLink(String link) {
        String result = linkTag.parse(link, null);
        Assert.assertEquals("\\href{" + link + "}{" + link + "}", result);
    }
    
    private void testHttpAndHttps(String textBefore, String link, String textAfter) {
        String http = textBefore + "http://" + link + textAfter;
        String text = linkTag.parse(http, null);
        assertEquals(textBefore + "\\href{http://" + link + "}{http://" + link + "}" + textAfter, text);

        String https = textBefore + "https://" + link + textAfter;
        text = linkTag.parse(https, null);
        assertEquals(textBefore + "\\href{https://" + link + "}{https://" + link + "}" + textAfter, text);
    }

}
