package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.parser.SimpleIndentator;

public class XmlTagTest {

	private static final String BEGIN = XmlTag.BEGIN;
	private static final String END = XmlTag.END;
	
	@Test
	public void testSpecialXmlTags() {
		String text = "\n<?xml version=1.0>";
		String result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\texspecial \\verb#<#?xml~version\\verb#=#1.0\\verb#>#" + END, result);
		
		text = "\n<!DOCTYPE BLAH BLAH BLAH>";
		result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\texspecial \\verb#<#!DOCTYPE~BLAH~BLAH~BLAH\\verb#>#" + END, result);
	}
	
	@Test
	public void testComments() {
		String text = "\n<!--some comment-->";
		String result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\texcomment \\verb#<#!\\verb#-#\\verb#-#some~comment\\verb#-#\\verb#-#\\verb#>#" + END, result);
		
		text = "\n<!--some multiline comment\n" +
				"some more comments  -->";
		result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\texcomment \\verb#<#!\\verb#-#\\verb#-#some~multiline~comment\\\\\n" +
				"\\texnormal \\texcomment some~more~comments~~\\verb#-#\\verb#-#\\verb#>#" + END, result);
	}
	
	@Test
	public void testSimpleTagWithoutAttribs() {
		String text = "\n<tag/>";
		String result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\textag \\verb#<#tag\\verb#/#\\verb#>#" + END, result);
		
		text = "\n<tag />";
		result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\textag \\verb#<#tag~\\textag \\verb#/#\\verb#>#" + END, result);
	}
	
	@Test
	public void testSimpleTagWithAttribs() {
		String text = "\n<tag attrib=\"value\"/>";
		String result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\textag \\verb#<#tag~\\texattrib attrib\\verb#=#\\texvalue \\verb#\"#value\\verb#\"#\\textag \\verb#/#\\verb#>#" + END, result);
		
		text = "\n<tag question=\"?\"\n" +
				"answer=\"42\" />";
		result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\textag \\verb#<#tag~\\texattrib question\\verb#=#\\texvalue \\verb#\"#?\\verb#\"#\\\\\n" +
				"\\texnormal \\texattrib answer\\verb#=#\\texvalue \\verb#\"#42\\verb#\"#~\\textag \\verb#/#\\verb#>#" + END, result);
	}
	
	@Test
	public void testTagWithBody() {
		String text = "\n<tag>body</tag>";
		String result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\textag \\verb#<#tag\\verb#>#\\texnormal body\\textag \\verb#<#\\verb#/#tag\\verb#>#" + END, result);
		
		text = "\n<tag>\n" +
				"    multiline\n" +
				"    body\n" +
				"</tag>";
		result = new XmlTag(new SimpleIndentator()).parse(text, "");
		Assert.assertEquals(BEGIN + "\\texnormal \\textag \\verb#<#tag\\verb#>#\\texnormal \\\\\n"+
				"\\texnormal \\texnormal ~~~~multiline\\\\\n"+
				"\\texnormal \\texnormal ~~~~body\\\\\n"+
				"\\texnormal \\texnormal \\textag \\verb#<#\\verb#/#tag\\verb#>#" + END, result);
	}
	
}
