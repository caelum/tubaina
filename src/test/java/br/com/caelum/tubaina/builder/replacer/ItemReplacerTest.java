package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.parser.MockedParser;
import br.com.caelum.tubaina.resources.Resource;

public class ItemReplacerTest {

	private List<Resource> resources;

	@Before
	public void setUp() {
		this.resources = new ArrayList<Resource>();
	}

	@Test
	public void testJavaCodeInsideItem() {
		String test = "* quero que o codigo java abaixo não tenha itens \n" + "[java]blah blah[/java]" + "[java] \n"
				+ "/**\n" + " * texto qualquer\n\n" + " *outro comentario\n" + "[/java]\n"
				+ "  *mas que isso seja outro item";
		List<Chunk> chunks = new ChunkSplitter(resources, ReplacerType.LIST).splitChunks(test);
		MockedParser parser = new MockedParser();
		Assert.assertEquals(2, chunks.size());
		Assert.assertEquals(ItemChunk.class, chunks.get(0).getClass());
		Assert.assertEquals("quero que o codigo java abaixo não tenha itens" + "blah blah \n" + "/**\n"
				+ " * texto qualquer\n\n" + " *outro comentario\n", chunks.get(0).getContent(parser));
		Assert.assertEquals(ItemChunk.class, chunks.get(1).getClass());
		Assert.assertEquals("mas que isso seja outro item", chunks.get(1).getContent(parser));

	}

	@Test(expected = RuntimeException.class)
	public void testItensWithParagraphs() throws Exception {
		String test = "blah blah blah \n    *uma lista, com coisas...\n  * outra lista";

		new ChunkSplitter(resources, ReplacerType.LIST).splitChunks(test);
	}
}
