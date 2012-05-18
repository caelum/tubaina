package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

public class NoteTagTest {
	
	@Test
	public void testNoteTag(){
		String result = new NoteTag().parse("qualquer texto de nota", null);
		Assert.assertEquals("\\begin{tubainanote}\nqualquer texto de nota\n\\end{tubainanote}", result);
	}
	
}
