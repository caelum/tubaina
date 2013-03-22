package br.com.caelum.tubaina.parser.html.kindle;

import junit.framework.Assert;

import org.junit.Test;

public class TableColumnTagTest {
	
	@Test
	public void testTableColumn() {
		TableColumnTag tag = new TableColumnTag();
		String result = tag.parse(chunk);
		Assert.assertEquals("<td>algum texto</td>", result);
	}
	
}
