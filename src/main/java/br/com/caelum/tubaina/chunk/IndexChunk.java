package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class IndexChunk implements Chunk{

	private final String name;
	public IndexChunk(String name) {
		this.name = name;
		
	}
	public String asString(Parser p) {
		return p.parseIndex(name);
	}

}
