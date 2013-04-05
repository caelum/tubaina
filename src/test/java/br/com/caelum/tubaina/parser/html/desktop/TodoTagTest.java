package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.TodoChunk;

public class TodoTagTest extends AbstractTagTest {

	@Test
	public void todoContentIsEmpty() throws Exception {
		TodoChunk chunk = new TodoChunk("Something I have to do later");
		String content = getContent(chunk);
		Assert.assertTrue(content.isEmpty());
	}
}
