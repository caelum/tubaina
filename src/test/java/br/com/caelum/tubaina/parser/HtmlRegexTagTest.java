package br.com.caelum.tubaina.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class HtmlRegexTagTest extends RegexTagTest {
    
    @Before
    public void setUp() throws IOException {
        regexTags = new RegexConfigurator().read("/regex.properties", "/html.properties");
    }
    
    @Test
    public void shouldReplaceSimpleLink() throws Exception {
        String text = "Here is a link: http://caelum.com.br";
        text = parseWithRegexps(text);
        assertEquals("Here is a link: <a href=\"http://caelum.com.br\">http://caelum.com.br</a>", text);
    }
    
    @Test
    public void shouldNotConsiderHtmlParagraphTag() throws Exception {
        String text = "<p>fórum que vai te auxiliar nesses quesitos (http://www.tectura.com.br).</p>";
        String expected = "<p>fórum que vai te auxiliar nesses quesitos (<a href=\"http://www.tectura.com.br\">http://www.tectura.com.br</a>).</p>";
        text = parseWithRegexps(text);
        assertEquals(expected, text);
    }
    
    @Test
    public void shouldReplaceLinkBeforeDot() throws Exception {
        String text = "link http://www.tectura.com.br.";
        String expected = "link <a href=\"http://www.tectura.com.br\">http://www.tectura.com.br</a>.";
        text = parseWithRegexps(text);
        assertEquals(expected, text);
    }
    
    @Test
    public void shouldReplaceLinkBeforeDoublePercent() throws Exception {
        String text = "identificado como %%oauth2:https://www.googleapis.com/auth/calendar%%.";
        String expected = "identificado como <code>oauth2:<a href=\"https://www.googleapis.com/auth/calendar\">https://www.googleapis.com/auth/calendar</a></code>.";
        text = parseWithRegexps(text);
        assertEquals(expected, text);
    }
}
