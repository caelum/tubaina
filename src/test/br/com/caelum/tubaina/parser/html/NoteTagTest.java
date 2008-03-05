package br.com.caelum.tubaina.parser.html;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.NoteTag;

public class NoteTagTest {

	@Test
	public void testNoteTag(){
		String result = new NoteTag().parse("qualquer texto de nota", null);
		Assert.assertEquals(result, "");
	}
	
}
