package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.TubainaSyntaxErrorsException;
import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.resources.Resource;

public class TableRowReplacerTest {
	private TableRowReplacer replacer;
	private List<Chunk> chunks;
	private List<Resource> resources;

	@Before
	public void setUp() {
		replacer = new TableRowReplacer(resources);
		chunks = new ArrayList<Chunk>();
		resources = new ArrayList<Resource>();
	}

	@Test
	public void testReplacesCorrectlyWithAColInside() {
		String cell = "[row][col]some text[/col][/row] text after";
		Assert.assertTrue(replacer.accepts(cell));
		String rest = replacer.execute(cell, chunks);
		Assert.assertEquals(" text after", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(TableRowChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testReplacesCorrectlyWithMultipleColsInside() {
		String cell = "[row][col]some text[/col][col]some more text[/col][/row] text after";
		Assert.assertTrue(replacer.accepts(cell));
		String rest = replacer.execute(cell, chunks);
		Assert.assertEquals(" text after", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(TableRowChunk.class, chunks.get(0).getClass());
	}

	@Test(expected = TubainaSyntaxErrorsException.class)
	public void testDoesntAcceptWithoutColsInside() {
		String cell = "[row]some text[/row] text after";
		Assert.assertTrue(replacer.accepts(cell));
		replacer.execute(cell, chunks);
	}

	@Test(expected = TubainaException.class)
	public void testDoesntAcceptWithoutEndTag() {
		String cell = "[row]some text after";
		Assert.assertTrue(replacer.accepts(cell));
		replacer.execute(cell, chunks);
	}

	@Test(expected = IllegalStateException.class)
	public void testDoesntAcceptWithoutBeginTag() {
		String cell = "some text before[/row]";
		Assert.assertFalse(replacer.accepts(cell));
		replacer.execute(cell, chunks);
	}
}
