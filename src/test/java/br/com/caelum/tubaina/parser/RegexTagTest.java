package br.com.caelum.tubaina.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class RegexTagTest {

    @Test
    public void test() throws IOException {
        List<Tag> regexTags = new RegexConfigurator().read("/regex.properties", "/latex.properties");
        String text = "[ref-label lala_lala]";
        for (Tag tag : regexTags) {
            text = tag.parse(text, "");
        }
        assertEquals("\\ref{lala_lala}", text);
        
    }

}
