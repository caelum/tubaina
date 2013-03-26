package br.com.caelum.tubaina.parser.latex;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.ItemChunk;

public class ItemTagTest extends AbstractTagTest {
	@Test
	public void testItem() {
		ItemChunk chunk = new ItemChunk(text("texto do item"));
		String result = getContent(chunk);
		Assert.assertEquals("\n\\item{texto do item}\n", result);
	}

	@Test
	public void testItemSplitt() {
		List<Chunk> chunks = new ChunkSplitter(null, "list").splitChunks(
				"* blah\n\n*bleh\n \n *  blih  ");
		Assert.assertEquals(3, chunks.size());
	}
}
