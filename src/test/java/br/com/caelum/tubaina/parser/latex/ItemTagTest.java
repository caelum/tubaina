package br.com.caelum.tubaina.parser.latex;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.builder.replacer.ReplacerType;

public class ItemTagTest {
	@Test
	public void testItem() {
		String result = new ItemTag().parse("texto do item", null);
		Assert.assertEquals("\n\\item{texto do item}\n", result);
	}

	@Test
	public void testItemSplitt() {
		List<Chunk> chunks = new ChunkSplitter(null, ReplacerType.LIST).splitChunks(
				"* blah\n\n*bleh\n \n *  blih  ");
		Assert.assertEquals(3, chunks.size());
	}
}
