package br.com.caelum.tubaina.resources;

import java.io.File;

public class ImageResource implements Resource {
	
	private final File img;
	private final String scale;

	public ImageResource(File img, String scale) {
		this.img = img;
		this.scale = scale;
	}

	public void copyTo(ResourceManipulator manipulator) {
		manipulator.copyImage(img, scale);
	}

}
