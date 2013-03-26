package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.NoteChunk;

public class NoteTagTest extends AbstractTagTest {

	@Test
	public void testNoteTag(){
		NoteChunk chunk = new NoteChunk(text(""), text("qualquer texto de nota"));
		String result = getContent(chunk);
		Assert.assertEquals(result, "<div class=\"note\">qualquer texto de nota</div>");
	}
	
}
