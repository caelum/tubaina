package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

public class TableRowTagTest {
	
	@Test
	public void testTableRowTag() {
		TableRowTag tag = new TableRowTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("linha da tabela\\\\", result);
	}
	
	@Test
	public void testRemoveLastColumnBreak() {
		TableRowTag tag = new TableRowTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("coluna1& coluna2& coluna3\\\\", result);
	}
}
