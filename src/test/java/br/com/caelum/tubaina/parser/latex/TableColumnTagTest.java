package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.TableColumnChunk;

public class TableColumnTagTest extends AbstractTagTest {

	@Test
	public void testTableColumnTag() {
		TableColumnChunk chunk = new TableColumnChunk(text("texto da coluna"));
		String result = getContent(chunk);
		Assert.assertEquals("texto da coluna& ", result);
	}

}
