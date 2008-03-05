package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

public class NoteTagTest {
	
	@Test
	public void testNoteTag(){
		String result = new NoteTag().parse("qualquer texto de nota", "Nota para o Instrutor");
		Assert.assertEquals("\\boxtag{Nota para o Instrutor}{qualquer texto de nota}", result);
	}
	
	@Test
	public void testChangeTitleToEmptyStringIfItsNull(){
		String result = new NoteTag().parse("Texto", null);
		Assert.assertEquals("\\boxtag{}{Texto}", result);
	}
	

}
