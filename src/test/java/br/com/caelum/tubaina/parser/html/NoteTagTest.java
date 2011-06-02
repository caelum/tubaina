package br.com.caelum.tubaina.parser.html;

import org.junit.Assert;
import org.junit.Test;

public class NoteTagTest {

	@Test
	public void testNoteTag(){
		String result = new NoteTag().parse("qualquer texto de nota", null);
		Assert.assertEquals(result, "<div class=\"note\">qualquer texto de nota</div>");
	}
	
}
