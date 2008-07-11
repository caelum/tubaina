package br.com.caelum.tubaina.parser.html;

import junit.framework.Assert;

import org.junit.Test;

public class TableRowTagTest {
	
	@Test
	public void testTableRow() {
		TableRowTag tag = new TableRowTag();
		String result = tag.parse("texto", null);
		Assert.assertEquals("<tr>texto</tr>", result);
	}
}
