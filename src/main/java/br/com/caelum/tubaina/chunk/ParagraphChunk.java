package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class ParagraphChunk implements Chunk{

	private String content;
	
	public ParagraphChunk(String content) {
		this.content = content;
	}

	public String getContent(Parser p) {
		return p.parseParagraph(this.content);
	}

}
