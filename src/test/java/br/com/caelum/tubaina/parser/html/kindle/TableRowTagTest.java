package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.TableRowChunk;

public class TableRowTagTest extends AbstractTagTest {
	
	@Test
	public void testTableRow() {
		TableRowChunk chunk = new TableRowChunk(text("texto"));
		String result = getContent(chunk);
		Assert.assertEquals("<tr>texto</tr>", result);
	}
}
