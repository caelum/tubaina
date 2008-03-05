package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.JavaChunk;

public class JavaReplacerTest {

	private JavaReplacer replacer;
	private List<Chunk> chunks;

	@Before
	public void setUp() {
		replacer = new JavaReplacer();
		chunks = new ArrayList<Chunk>();
	}

	@Test
	public void testReplacesCorrectJava() {
		String java = "[java]public class Teste()[/java] ola resto";
		Assert.assertTrue(replacer.accepts(java));
		String resto = replacer.execute(java, chunks);
		Assert.assertEquals(" ola resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(JavaChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testDoesntAcceptWithoutEndTag() {
		String java = "[java]public class Teste() ola resto";
		Assert.assertTrue(replacer.accepts(java));
		try {
			replacer.execute(java, chunks);
			Assert.fail("Should raise an exception");
		} catch (TubainaException e) {
			// OK
		}
		Assert.assertEquals(0, chunks.size());
	}

	@Test
	public void testDoesntAcceptWithoutBeginTag() {
		String java = "public class Teste()[/java] ola resto";
		Assert.assertFalse(replacer.accepts(java));
		try {
			replacer.execute(java, chunks);
			Assert.fail("Should raise an exception");
		} catch (IllegalStateException e) {
			// OK
		}
		Assert.assertEquals(0, chunks.size());
	}
}
