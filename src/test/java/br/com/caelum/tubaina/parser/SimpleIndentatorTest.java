package br.com.caelum.tubaina.parser;

import junit.framework.Assert;

import org.junit.Test;


public class SimpleIndentatorTest {

	@Test
	public void testIndentatorOnlySpacesSingleLine() {
		String test = "   blah";
		String result = new SimpleIndentator().indent(test);
		Assert.assertEquals("blah", result);
	}
	
	@Test
	public void testIndentatorOnlySpacesMultiLine() {
		String test = 
				"   blah\n" +
				"     bleh\n" +
				"       blih\n" +
				"     bloh\n" +
				"   bluh";
		String result = new SimpleIndentator().indent(test);
		Assert.assertEquals(
				"blah\n" +
				"  bleh\n" +
				"    blih\n" +
				"  bloh\n" +
				"bluh", result);
	}
	
	@Test
	public void testIndentatorWithTabsMultiLine() {
		String test = 
				"	blah\n" +
				"		bleh\n" +
				"			blih\n" +
				"		bloh\n" +
				"	bluh";
		String result = new SimpleIndentator().indent(test);
		Assert.assertEquals(
				"blah\n" +
				"    bleh\n" +
				"        blih\n" +
				"    bloh\n" +
				"bluh", result);
	}
}
