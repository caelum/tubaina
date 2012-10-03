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

}
