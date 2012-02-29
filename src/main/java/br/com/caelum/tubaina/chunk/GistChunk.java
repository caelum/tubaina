package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class GistChunk implements Chunk {


	private final String options;

	public GistChunk(String options) {
		this.options = options;
	}

	public String getContent(Parser p) {
		return p.parseGist(this.options);
	}
	

}
