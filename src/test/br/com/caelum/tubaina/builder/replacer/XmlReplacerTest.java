package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.XmlChunk;

public class XmlReplacerTest {

	private XmlReplacer replacer;

	private List<Chunk> chunks;

	@Before
	public void setUp() {
		replacer = new XmlReplacer();
		chunks = new ArrayList<Chunk>();
	}

	@Test
	public void testReplacesCorrectXml() {
		String xml = "[xml]<bla slkdj> skj</bla>[/xml] oi resto";
		Assert.assertTrue(replacer.accepts(xml));
		String resto = replacer.execute(xml, chunks);
		Assert.assertEquals(" oi resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(XmlChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testDoesntAcceptWithoutEndTag() {
		String xml = "[xml]<bla slkdj> skj</bla> oi resto";
		Assert.assertTrue(replacer.accepts(xml));
		try {
			replacer.execute(xml, chunks);
			Assert.fail("Should raise an exception");
		} catch (TubainaException e) {
			// OK
		}
		Assert.assertEquals(0, chunks.size());
	}

	@Test
	public void testDoesntAcceptWithoutBeginTag() {
		String xml = "<bla slkdj> skj</bla>[/xml] oi resto";
		Assert.assertFalse(replacer.accepts(xml));
		try {
			replacer.execute(xml, chunks);
			Assert.fail("Should raise an exception");
		} catch (IllegalStateException e) {
			// OK
		}
		Assert.assertEquals(0, chunks.size());
	}
}
