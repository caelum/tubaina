package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.chunk.RubyChunk;

public class RubyReplacerTest {
	
	private RubyReplacer replacer;
	private List<Chunk> chunks;

	@Before
	public void setUp() {
		replacer = new RubyReplacer();
		chunks = new ArrayList<Chunk>();
	}

	@Test
	public void testReplacesCorrectCode() {
		String code = "[ruby]ola mundo[/ruby] ola resto";
		Assert.assertTrue(replacer.accepts(code));
		String resto = replacer.execute(code, chunks);
		Assert.assertEquals(" ola resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(RubyChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testDoesntAcceptWithoutEndTag() {
		String code = "[ruby]ola mundo ola resto";
		Assert.assertTrue(replacer.accepts(code));
		try {
			replacer.execute(code, chunks);
			Assert.fail("Should raise an exception");
		} catch (TubainaException e) {
			// OK
		}
		Assert.assertEquals(0, chunks.size());
	}

	@Test
	public void testDoesntAcceptWithoutBeginTag() {
		String answer = "ola mundo[/ruby] ola resto";
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
