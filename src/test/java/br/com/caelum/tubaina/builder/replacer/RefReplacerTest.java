package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.RefChunk;

public class RefReplacerTest {

    private RefReplacer replacer;
    private List<Chunk> chunks;

    @Before
    public void setUp() {
        replacer = new RefReplacer();
        chunks = new ArrayList<Chunk>();
    }

    @Test
    public void testReplacesLowerCaseRef() {
        String todo = "[ref something, to, do] ola resto";
        Assert.assertTrue(replacer.accepts(todo));
        String resto = replacer.execute(todo, chunks);
        Assert.assertEquals(" ola resto", resto);
        Assert.assertEquals(1, chunks.size());
        Assert.assertEquals(RefChunk.class, chunks.get(0).getClass());
    }

    @Test
    public void testReplacesUpperCaseTodo() {
        String todo = "[ref something to do2] ola resto";
        Assert.assertTrue(replacer.accepts(todo));
        String resto = replacer.execute(todo, chunks);
        Assert.assertEquals(" ola resto", resto);
        Assert.assertEquals(1, chunks.size());
        Assert.assertEquals(RefChunk.class, chunks.get(0).getClass());
    }

    @Test
    public void testReplacesTodoWithNoContent() {
        String todo = "[ref] ola resto";
        Assert.assertTrue(replacer.accepts(todo));
        String resto = replacer.execute(todo, chunks);
        Assert.assertEquals(" ola resto", resto);
        Assert.assertEquals(1, chunks.size());
        Assert.assertEquals(RefChunk.class, chunks.get(0).getClass());
    }
}
