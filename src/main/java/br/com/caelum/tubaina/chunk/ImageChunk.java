package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;


public class ImageChunk extends AbstractChunk<ImageChunk> {

	private final String options;
	private final String path;
	private final double width;
	private final int dpi;

	public ImageChunk(String path, String options, double width, int dpi) {
		this.path = path;
		this.options = options;
		this.width = width;
		this.dpi = dpi;
	}		

	public String getOptions() {
		return options;
	}
	
	public String getPath() {
		return path;
	}
	
	public double getWidth() {
		return width;
	}
	
	public int getDpi() {
		return dpi;
	}
}
