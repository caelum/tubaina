package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

public class TableColumnTagTest {

	@Test
	public void testTableColumnTag() {
		TableColumnTag tag = new TableColumnTag();
		String result = tag.parse("texto da coluna", null);
		Assert.assertEquals("texto da coluna& ", result);
	}

}
