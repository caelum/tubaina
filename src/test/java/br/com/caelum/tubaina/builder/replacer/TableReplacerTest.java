package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.resources.Resource;

public class TableReplacerTest {
	private TableReplacer replacer;
	private List<Chunk> chunks;
	private List<Resource> resources;
	
	@Before
	public void setUp() {
		resources = new ArrayList<Resource>();
		replacer = new TableReplacer(resources);
		chunks = new ArrayList<Chunk>();
	}
	
	@Test
	public void testReplacesCorrectlyWithRowTagInside() {
		String table = "[table][row][col]celula[/col][/row][/table] resto de texto";
		Assert.assertTrue(replacer.accepts(table));
		String rest = replacer.execute(table, chunks);
		Assert.assertEquals(" resto de texto", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(TableChunk.class, chunks.get(0).getClass());
	}
	
	@Test
	public void testReplacesCorrectlyWithMultipleRowsInside() {
		String table = "[table][row][col]celula[/col][col]outra celula[/col][/row][row][col]mais uma celula[/col][col]ultima[/col][/row][/table] resto de texto";
		Assert.assertTrue(replacer.accepts(table));
		String rest = replacer.execute(table, chunks);
		Assert.assertEquals(" resto de texto", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(TableChunk.class, chunks.get(0).getClass());
	}
	
	@Test
	public void testReplacesCorrectlyWithTitle() {
		String table = "[table \"titulo\"][row][col]celula[/col][/row][/table] resto de texto";
		Assert.assertTrue(replacer.accepts(table));
		String rest = replacer.execute(table, chunks);
		Assert.assertEquals(" resto de texto", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(TableChunk.class, chunks.get(0).getClass());
	}
	
	@Test
	public void testReplacesCorrectlyWithNoBorderOption() {
		String table = "[table noborder][row][col]celula[/col][/row][/table] resto de texto";
		Assert.assertTrue(replacer.accepts(table));
		String rest = replacer.execute(table, chunks);
		Assert.assertEquals(" resto de texto", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(TableChunk.class, chunks.get(0).getClass());
	}
	
	@Test
	public void testReplacesCorrectlyWithTitleAndNoBorderOption() {
		String table = "[table \"titulo\" noborder][row][col]celula[/col][/row][/table] resto de texto";
		Assert.assertTrue(replacer.accepts(table));
		String rest = replacer.execute(table, chunks);
		Assert.assertEquals(" resto de texto", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(TableChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testReplacesCorrectlyWithCompositeTitle() {
		String table = "[table \"titulo bem grande\"][row][col]celula[/col][/row][/table] resto de texto";
		Assert.assertTrue(replacer.accepts(table));
		String rest = replacer.execute(table, chunks);
		Assert.assertEquals(" resto de texto", rest);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(TableChunk.class, chunks.get(0).getClass());
	}
		
	@Test
	public void testDoesntAcceptWithoutRowTagInside() {
		String table = "[table]texto[/table] resto";
		Assert.assertTrue(replacer.accepts(table));
		try {
			replacer.execute(table, chunks);
			Assert.fail("Should throw an exception");
		} catch (TubainaException e) {
			// ok
		}
		Assert.assertEquals(0, chunks.size());
	}
	
	@Test
	public void testDoesntAcceptWithOnlyColTagInside() {
		String table = "[table][col]texto[/col][/table] resto";
		Assert.assertTrue(replacer.accepts(table));
		try {
			replacer.execute(table, chunks);
			Assert.fail("Should throw an exception");
		} catch (TubainaException e) {
			// ok
		}
		Assert.assertEquals(0, chunks.size());
	}
	
	@Test
	public void testDoesntAcceptWithoutEndTag() {
		String table = "[table][row][col]texto[/col][/row] resto";
		Assert.assertTrue(replacer.accepts(table));
		try {
			replacer.execute(table, chunks);
			Assert.fail("Should throw an exception");
		} catch (TubainaException e) {
			// ok
		}
		Assert.assertEquals(0, chunks.size());
	}
	
	@Test
	public void testDoesntAcceptWithoutBeginTag() {
		String table = "[row][col]texto[/col][/row][/table] resto";
		Assert.assertFalse(replacer.accepts(table));
		try {
			replacer.execute(table, chunks);
			Assert.fail("Should throw an exception");
		} catch (IllegalStateException e) {
			// ok
		}
		Assert.assertEquals(0, chunks.size());
	}
}
