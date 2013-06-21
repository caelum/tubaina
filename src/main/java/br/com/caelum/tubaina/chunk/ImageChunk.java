package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;
import br.com.caelum.tubaina.SectionsManager;


public class ImageChunk extends AbstractChunk<ImageChunk> {

	private final String options;
	private final String path;
	private final double width;
	private final int dpi;
	private final int imageNumber;
	private final int chapterNumber;

	public ImageChunk(String path, String options, double width, 
			int dpi, SectionsManager sectionsManager) {
		this.path = path;
		this.options = options == null ? "" : options;
		this.width = width;
		this.dpi = dpi;
		this.imageNumber = sectionsManager.nextImage();
		this.chapterNumber = sectionsManager.getCurrentChapter();
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
	
	public int getChapterNumber() {
		return chapterNumber;
	}
	
	public int getImageNumber() {
		return imageNumber;
	}
}
