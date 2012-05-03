package br.com.caelum.tubaina.parser.html.desktop;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.html.desktop.TableRowTag;

public class TableRowTagTest {
	
	@Test
	public void testTableRow() {
		TableRowTag tag = new TableRowTag();
		String result = tag.parse("texto", null);
		Assert.assertEquals("<tr>texto</tr>", result);
	}
}
