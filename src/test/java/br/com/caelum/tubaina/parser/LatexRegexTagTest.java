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
    public void shouldReplaceBoldAndItalic() throws Exception {
    	String text = "blablabla **%%bold and italic%%**";
    	text = parseWithRegexps(text);
    	assertEquals("blablabla \\definition{\\codechunk{bold and italic}}", text);
    }
    
    @Test
    public void shouldReplaceSubsection() throws Exception {
    	String text = "[subsection subsection title]";
    	text = parseWithRegexps(text);
    	assertEquals("\\subsection{subsection title}", text);
    }
    
}
