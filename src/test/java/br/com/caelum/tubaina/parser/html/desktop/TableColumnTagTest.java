package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.TableColumnChunk;

public class TableColumnTagTest extends AbstractTagTest {
	
	@Test
	public void testTableColumn() {
		TableColumnChunk chunk = new TableColumnChunk(text("algum texto"));
		String result = getContent(chunk);
		Assert.assertEquals("<td>algum texto</td>", result);
	}
	
}
