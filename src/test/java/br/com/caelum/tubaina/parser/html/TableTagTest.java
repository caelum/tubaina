package br.com.caelum.tubaina.parser.html;

import junit.framework.Assert;

import org.junit.Test;

public class TableTagTest {
	
	@Test
	public void testTable() {
		TableTag tag = new TableTag();
		String result = tag.parse("texto da tabela", "");
		Assert.assertEquals("<table border=1>texto da tabela</table>", result);
	}
	
	@Test
	public void testTableWithTitle() {
		TableTag tag = new TableTag();
		String result = tag.parse("texto da tabela", "titulo");
		Assert.assertEquals("<h3>titulo</h3><table border=1>texto da tabela</table>", result);
	}
	
	@Test
	public void testTableWithoutBorder() {
		TableTag tag = new TableTag(true);
		String result = tag.parse("texto da tabela", "");
		Assert.assertEquals("<table>texto da tabela</table>", result);
	}
	
	@Test
	public void testTableWithTitleAndWithoutBorder() {
		TableTag tag = new TableTag(true);
		String result = tag.parse("texto da tabela", "titulo");
		Assert.assertEquals("<h3>titulo</h3><table>texto da tabela</table>", result);
	}
}
