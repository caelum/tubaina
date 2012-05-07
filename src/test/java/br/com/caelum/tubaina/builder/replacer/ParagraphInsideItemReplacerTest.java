package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.ParagraphInsideItemChunk;

public class ParagraphInsideItemReplacerTest {
    private List<Chunk> chunks;
    private ParagraphInsideItemReplacer replacer;
    
    @Before
    public void setUp() {
        // just to test, set the exercise tag as the only paragraph terminator tag
        replacer = new ParagraphInsideItemReplacer("exercise");
        chunks = new ArrayList<Chunk>();
    }
    
    @Test
    public void testAcceptSimpleParagraph() {
        String text = "some text.";
        Assert.assertTrue(replacer.accepts(text));
        String remaining = replacer.execute(text, chunks);
        Assert.assertEquals("", remaining);
        Assert.assertEquals(1, chunks.size());
        Assert.assertEquals(ParagraphInsideItemChunk.class, chunks.get(0).getClass());
    }
    
    @Test
    public void testDoesntAcceptQuestionTagInsideParagraph() {
        String text = "[question]question[/question]";
        Assert.assertFalse("Should not accept question tag inside paragraph", replacer.accepts(text));
    }
}