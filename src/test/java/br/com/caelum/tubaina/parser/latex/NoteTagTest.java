package br.com.caelum.tubaina.parser.latex;

import org.junit.Assert;
import org.junit.Test;

public class NoteTagTest {
	
	@Test
	public void testNoteTag(){
		String result = new NoteTag().parse("qualquer texto de nota", "Nota para o Instrutor");
		Assert.assertEquals("\\begin{tubainabox}{Nota para o Instrutor}\nqualquer texto de nota\n\\end{tubainabox}", result);
	}
	
	@Test
	public void noteTagShouldAlwaysHaveBoxName(){
		String result = new NoteTag().parse("note without a title should get a standart title", null);
		Assert.assertEquals("\\begin{tubainabox}{Instructor's note}\nnote without a title should get a standart title\n\\end{tubainabox}", result);
	}
	
}
