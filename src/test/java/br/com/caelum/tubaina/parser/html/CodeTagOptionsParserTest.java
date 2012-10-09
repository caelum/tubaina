package br.com.caelum.tubaina.parser.html;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CodeTagOptionsParserTest {

    @Test
    public void shouldParseHighlights() {
        CodeTagOptionsParser codeTagOptionsParser = new CodeTagOptionsParser();
        String options = "h=1,2,3,4";
        List<Integer> lines = codeTagOptionsParser.parseHighlights(options);
        List<Integer> expected = Arrays.asList(1,2,3,4);
        assertEquals(4, lines.size());
        assertEquals(expected, lines);
    }
    
    @Test
    public void shouldParseLanguage() {
        CodeTagOptionsParser codeTagOptionsParser = new CodeTagOptionsParser();
        String options = "java h=1,2,3,4";
        String language = codeTagOptionsParser.parseLanguage(options);
        assertEquals("java", language);
    }
    
    @Test
    public void shouldParseLabel() {
        CodeTagOptionsParser codeTagOptionsParser = new CodeTagOptionsParser();
        String options = "java label=javacode h=1,2,3,4";
        String label = codeTagOptionsParser.parseLabel(options);
        assertEquals("javacode", label);
    }
    
    @Test
    public void shouldParseCSharp() {
        CodeTagOptionsParser codeTagOptionsParser = new CodeTagOptionsParser();
        String options = "C# #";
        String label = codeTagOptionsParser.parseLanguage(options);
        assertEquals("C#", label);
    }
    
    @Test
    public void shouldParseFileName() {
        CodeTagOptionsParser codeTagOptionsParser = new CodeTagOptionsParser();
        String options = "filename=src/Main.java";
        String label = codeTagOptionsParser.parseFileName(options);
        assertEquals("src/Main.java", label);
    }
    
    @Test
    public void shouldParseJavascriptLang() {
        CodeTagOptionsParser codeTagOptionsParser = new CodeTagOptionsParser();
        String options = "javascript";
        String lang = codeTagOptionsParser.parseLanguage(options);
        assertEquals("javascript", lang);
    }

}
