package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IndexTagTest {

    @Test
    public void shouldParseIndexTag() throws Exception {
        String result = new IndexTag().parse("name", "");
        assertEquals("\n\\index{name}\n", result);
    }
    
    @Test
    public void shouldEscapeUnderscores() throws Exception {
        String result = new IndexTag().parse("index_name_underscore", "");
        assertEquals("\n\\index{index\\_name\\_underscore}\n", result);
    }
}
