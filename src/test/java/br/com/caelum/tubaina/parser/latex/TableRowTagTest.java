package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.TableRowChunk;

public class TableRowTagTest extends AbstractTagTest {
	
	@Test
	public void testTableRowTag() {
		TableRowChunk chunk = new TableRowChunk(text("linha da tabela"));
		String result = getContent(chunk);
		Assert.assertEquals("linha da tabela\\\\", result);
	}
	
	@Test
	public void testRemoveLastColumnBreak() {
		TableRowChunk chunk = new TableRowChunk(text("coluna1& coluna2& coluna3&"));
		String result = getContent(chunk);
		Assert.assertEquals("coluna1& coluna2& coluna3\\\\", result);
	}
}
