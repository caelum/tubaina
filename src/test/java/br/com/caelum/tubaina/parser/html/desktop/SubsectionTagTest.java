package br.com.caelum.tubaina.parser.html.desktop;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.parser.html.desktop.SubsectionTag;

public class SubsectionTagTest extends AbstractTagTest{
	
	@Test
	public void shouldParseSubsectionChunk() {
		SubsectionTag subsectionTag = new SubsectionTag();
		SubsectionChunk chunk = new SubsectionChunk("title", text("content"), 1, 1, 1);
		String parsed = subsectionTag.parse(chunk);
		assertEquals("<"+SubsectionTag.HTML_TAG+" class='subsection'>1.1.1 - title</"+SubsectionTag.HTML_TAG+">\ncontent", parsed);
	}
	
	@Test
	public void shouldParseSubsectionChunkSanitized() {
		SubsectionTag subsectionTag = new SubsectionTag();
		SubsectionChunk chunk = new SubsectionChunk("éáçãà", text("content"), 1, 1, 1);
		String parsed = subsectionTag.parse(chunk);
		assertEquals("<"+SubsectionTag.HTML_TAG+" class='subsection'>1.1.1 - &eacute;&aacute;&ccedil;&atilde;&agrave;</"+SubsectionTag.HTML_TAG+">\ncontent", parsed);
	}

}
