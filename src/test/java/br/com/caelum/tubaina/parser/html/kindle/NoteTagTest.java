package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.NoteChunk;

public class NoteTagTest extends AbstractTagTest {

	@Test
	public void testNoteTag() {
		NoteChunk chunk = new NoteChunk(text(""), text("qualquer texto de nota"));
		String result = getContent(chunk);
		String begin = "---------------------------<br />";
		String end = "<br />---------------------------";
		Assert.assertEquals(result, begin + "qualquer texto de nota" + end);
	}

}
