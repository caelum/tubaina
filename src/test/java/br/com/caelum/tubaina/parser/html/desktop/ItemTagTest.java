package br.com.caelum.tubaina.parser.html.desktop;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.parser.html.desktop.ItemTag;

public class ItemTagTest {
	@Test
	public void testItem() {
		String result = new ItemTag().parse(chunk);
		Assert.assertEquals("<li>texto do item</li>", result);
	}

	@Test
	public void testItemSplitt() {
		List<Chunk> chunks = new ChunkSplitter(null, "list")
				.splitChunks("* blah\n\n*bleh\n \n *  blih  ");
		Assert.assertEquals(3, chunks.size() );
	}
	
	
}
