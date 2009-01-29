package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.resources.Resource;

public class QuestionReplacerTest {

	private QuestionReplacer replacer;
	private List<Chunk> chunks;
	private List<Resource> resources;

	@Before
	public void setUp() {
		replacer = new QuestionReplacer(resources);
		chunks = new ArrayList<Chunk>();
	}

	@Test
	public void testReplacesCorrectQuestion() {
		String question = "[question]ola mundo[/question] ola resto";
		Assert.assertTrue(replacer.accepts(question));
		String resto = replacer.execute(question, chunks);
		Assert.assertEquals(" ola resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(QuestionChunk.class, chunks.get(0).getClass());
	}

	@Test(expected = TubainaException.class)
	public void testDoesntAcceptWithoutEndTag() {
		String question = "[question]ola mundo ola resto";
		Assert.assertTrue(replacer.accepts(question));
		replacer.execute(question, chunks);
	}

	@Test(expected = IllegalStateException.class)
	public void testDoesntAcceptWithoutBeginTag() {
		String question = "ola mundo[/question] ola resto";
		Assert.assertFalse(replacer.accepts(question));
		replacer.execute(question, chunks);
	}
}
