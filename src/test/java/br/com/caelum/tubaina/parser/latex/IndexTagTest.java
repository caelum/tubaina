package br.com.caelum.tubaina.parser.latex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.tubaina.chunk.IndexChunk;

public class IndexTagTest extends AbstractTagTest {

    @Test
    public void shouldParseIndexTag() throws Exception {
        IndexChunk chunk = new IndexChunk("name");
		String result = getContent(chunk);
        assertEquals("\n\\index{name}\n", result);
    }
    
    @Test
    public void shouldEscapeUnderscores() throws Exception {
    	IndexChunk chunk = new IndexChunk("index_name_underscore");
        String result = getContent(chunk);
        assertEquals("\n\\index{index\\_name\\_underscore}\n", result);
    }
}
