package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.resources.Resource;

public class AnswerReplacerTest {
	
	private AnswerReplacer replacer;
	private List<Chunk> chunks;
	private List<Resource> resources;
	
	@Before
	public void setUp() {
		resources = new ArrayList<Resource>();
		replacer = new AnswerReplacer(resources);
		chunks = new ArrayList<Chunk>();
	}

	@Test
	public void testReplacesCorrectAnswer() {
		String answer = "[answer]ola mundo[/answer] ola resto";
		Assert.assertTrue(replacer.accepts(answer));
		String resto = replacer.execute(answer, chunks);
		Assert.assertEquals(" ola resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(AnswerChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testDoesntAcceptWithoutEndTag() {
		String answer = "[answer]ola mundo ola resto";
		Assert.assertTrue(replacer.accepts(answer));
		try {
			replacer.execute(answer, chunks);
			Assert.fail("Should raise an exception");
		} catch (TubainaException e) {
			// OK
		}
		Assert.assertEquals(0, chunks.size());
	}

	@Test
	public void testDoesntAcceptWithoutBeginTag() {
		String answer = "ola mundo[/answer] ola resto";
		Assert.assertFalse(replacer.accepts(answer));
		try {
			replacer.execute(answer, chunks);
			Assert.fail("Should raise an exception");
		} catch (IllegalStateException e) {
			// OK
		}
		Assert.assertEquals(0, chunks.size());
	}
}
