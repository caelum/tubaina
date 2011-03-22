package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

public class TableRowTagTest {
	
	@Test
	public void testTableRowTag() {
		TableRowTag tag = new TableRowTag();
		String result = tag.parse("linha da tabela", null);
		Assert.assertEquals("linha da tabela\\\\", result);
	}
	
	@Test
	public void testRemoveLastColumnBreak() {
		TableRowTag tag = new TableRowTag();
		String result = tag.parse("coluna1& coluna2& coluna3&", null);
		Assert.assertEquals("coluna1& coluna2& coluna3\\\\", result);
	}
}
