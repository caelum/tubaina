package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.ParagraphChunk;

public class ParagraphReplacerTest {
	private List<Chunk> chunks;
	private ParagraphReplacer replacer;
	
	@Before
	public void setUp() {
		// just to test, set the exercise tag as the only paragraph terminator tag
		replacer = new ParagraphReplacer("exercise");
		chunks = new ArrayList<Chunk>();
	}
	
	@Test
	public void testAcceptSimpleParagraph() {
		String text = "some text.";
		Assert.assertTrue(replacer.accepts(text));
		String remaining = replacer.execute(text, chunks);
		Assert.assertEquals("", remaining);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
	}
	
	@Test
	public void testAcceptMultipleParagraphs() {
		String text = "some text.\n\nsome more text.";
		Assert.assertTrue(replacer.accepts(text));
		String secondParagraph = replacer.execute(text, chunks);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals("\n\nsome more text.", secondParagraph);
		Assert.assertTrue(replacer.accepts(secondParagraph));
		String remaining = replacer.execute(secondParagraph, chunks);
		Assert.assertEquals("", remaining);
		Assert.assertEquals(2, chunks.size());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(1).getClass());
	}
	
	@Test
	public void testAcceptParagraphWithTagAfter() {
		String text = "some text.\n[exercise]";
		Assert.assertTrue(replacer.accepts(text));
		String remaining = replacer.execute(text, chunks);
		Assert.assertEquals("\n[exercise]", remaining);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
	}
	
	/**
	 * @see http://jira.caelum.com.br/browse/TUBAINA-9
	 */
	@Test
	public void testDoesntAcceptQuestionTagInsideParagraph() {
		String text = "[question]question[/question]";
		Assert.assertFalse("Should not accept question tag inside paragraph", replacer.accepts(text));
	}
}
