package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class IntroductionChunk implements CompositeChunk {

	private List<Chunk> chunks;
	
	public IntroductionChunk(List<Chunk> chunks) {
		this.chunks = chunks;
	}
	
	public String getContent(Parser p) {
		String content = "";
		for (Chunk c : chunks) {
			content += c.getContent(p);
		}
		return content;
	}

}
