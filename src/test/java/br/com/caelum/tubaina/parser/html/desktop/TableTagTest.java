package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.TableChunk;

public class TableTagTest extends AbstractTagTest {
	
	@Test
	public void testTable() {
		TableChunk chunk = new TableChunk("", text("texto da tabela"));
		String result = getContent(chunk);
		Assert.assertEquals("<table border=1>texto da tabela</table>", result);
	}
	
	@Test
	public void testTableWithTitle() {
		TableChunk chunk = new TableChunk("title=\"titulo\"", text("texto da tabela"));
		String result = getContent(chunk);
		Assert.assertEquals("<h3>titulo</h3><table border=1>texto da tabela</table>", result);
	}
	
	@Test
	public void testTableWithoutBorder() {
		TableChunk chunk = new TableChunk("noborder", text("texto da tabela"));
		String result = getContent(chunk);
		Assert.assertEquals("<table>texto da tabela</table>", result);
	}
	
	@Test
	public void testTableWithTitleAndWithoutBorder() {
		TableChunk chunk = new TableChunk("title=\"titulo\" noborder", text("texto da tabela"));
		String result = getContent(chunk);
		Assert.assertEquals("<h3>titulo</h3><table>texto da tabela</table>", result);
	}
}
