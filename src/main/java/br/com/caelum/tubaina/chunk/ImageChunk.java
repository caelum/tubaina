package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;


public class ImageChunk implements Chunk {

	private final String options;
	private final String path;
	private final double width;
	private final int dpi;

	public ImageChunk(String path, String options, double width, int dpi) {
		this.options = options;
		this.path = path;
		this.width = width;
		this.dpi = dpi;
	}		

	public String getContent(Parser p) {
		return p.parseImage(this.path,this.options + "[" + width + "," + dpi + "]");
	}

}
