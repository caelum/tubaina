package br.com.caelum.tubaina.parser.html.desktop;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.html.desktop.TableColumnTag;

public class TableColumnTagTest {
	
	@Test
	public void testTableColumn() {
		TableColumnTag tag = new TableColumnTag();
		String result = tag.parse("algum texto", null);
		Assert.assertEquals("<td>algum texto</td>", result);
	}
	
}
