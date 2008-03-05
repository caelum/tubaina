package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;


public class ImageChunk implements Chunk {

	private final String options;
	private final String path;
	private final int width;

	public ImageChunk(String path, String options, int width) {
		this.options = options;
		this.path = path;
		this.width = width;
	}		

	public String getContent(Parser p) {
		return p.parseImage(this.path,this.options + "[" + width + "]");
	}

}
