package br.com.caelum.tubaina.parser.html.desktop;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.parser.html.desktop.NoteTag;

public class NoteTagTest {

	@Test
	public void testNoteTag(){
		String result = new NoteTag().parse(chunk);
		Assert.assertEquals(result, "<div class=\"note\">qualquer texto de nota</div>");
	}
	
}
