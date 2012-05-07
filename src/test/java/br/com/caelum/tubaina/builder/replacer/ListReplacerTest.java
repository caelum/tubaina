package br.com.caelum.tubaina.builder.replacer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.chunk.ParagraphInsideItemChunk;
import br.com.caelum.tubaina.resources.Resource;

public class ListReplacerTest {

	private ListReplacer replacer;
	private List<Chunk> chunks;
	private List<Resource> resources;

	@Before
	public void setUp() {
		replacer = new ListReplacer(resources);
		chunks = new ArrayList<Chunk>();
	}

	@Test
	public void testReplacesCorrectList() {
		String list = "[list]*um item\n\n*outro item[/list] oi resto";
		Assert.assertTrue(replacer.accepts(list));
		String resto = replacer.execute(list, chunks);
		Assert.assertEquals(" oi resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(ListChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testCreateSublistsProperly() throws Exception {
		String list = "[list number]\n" + "   * primeiro item number\n" + "   [list roman]\n"
				+ "     *primeiro item roman\n" + "     [list letter]\n" + "        *primeiro item letter\n"
				+ "        *segundo item letter\n" + "     [/list]\n" + "     *segundo item roman\n"
				+ "     *terceiro item roman\n" + "   [/list]\n" + "*segundo item number\n" + "*terceiro item number\n"
				+ "[/list]";
		Assert.assertTrue(replacer.accepts(list));
		String resto = replacer.execute(list, chunks);
		Assert.assertEquals("", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(ListChunk.class, chunks.get(0).getClass());

		Field listBody = ListChunk.class.getDeclaredField("body");
		listBody.setAccessible(true);

		List<Chunk> chunks1 = (List<Chunk>) listBody.get(chunks.get(0));
		Assert.assertEquals(3, chunks1.size());
		Assert.assertEquals(ItemChunk.class, chunks1.get(0).getClass());
		Assert.assertEquals(ItemChunk.class, chunks1.get(1).getClass());
		Assert.assertEquals(ItemChunk.class, chunks1.get(2).getClass());

		Field itemBody = ItemChunk.class.getDeclaredField("body");
		itemBody.setAccessible(true);

		List<Chunk> chunks2 = (List<Chunk>) itemBody.get(chunks1.get(0));
		Assert.assertEquals(2, chunks2.size());
		Assert.assertEquals(ParagraphInsideItemChunk.class, chunks2.get(0).getClass());
		Assert.assertEquals(ListChunk.class, chunks2.get(1).getClass());
		List<Chunk> chunks3 = (List<Chunk>) listBody.get(chunks2.get(1));
		List<Chunk> chunks4 = (List<Chunk>) itemBody.get(chunks3.get(0));
		Assert.assertEquals(ListChunk.class, chunks4.get(1).getClass());
	}

	@Test
	public void testReplacesListWithNoItems() {
		String list = "[list]*texto solto[/list] oi resto";
		Assert.assertTrue(replacer.accepts(list));
		String resto = replacer.execute(list, chunks);
		Assert.assertEquals(" oi resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(ListChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testReplacesListWithListsInside() {
		String list = "[list]*texto solto[list]* blabla[/list] ahahah[/list] oi resto";
		Assert.assertTrue(replacer.accepts(list));
		String resto = replacer.execute(list, chunks);
		Assert.assertEquals(" oi resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(ListChunk.class, chunks.get(0).getClass());
	}

	@Test(expected = TubainaException.class)
	public void testMalformedNestedLists() throws Exception {
		String list = "[list]*um item[list]\n\n*outro item oi resto[/list]";
		Assert.assertTrue(replacer.accepts(list));
		replacer.execute(list, chunks);
	}

	@Test(expected = TubainaException.class)
	public void testDoesntAcceptWithoutEndTag() {
		String list = "[list]*um item\n\n*outro item oi resto";
		Assert.assertTrue(replacer.accepts(list));
		replacer.execute(list, chunks);
	}

	@Test(expected=IllegalStateException.class)
	public void testDoesntAcceptWithoutBeginTag() {
		String list = "*um item\n\n*outro item[/list] oi resto";
		Assert.assertFalse(replacer.accepts(list));
			replacer.execute(list, chunks);
	}

}
