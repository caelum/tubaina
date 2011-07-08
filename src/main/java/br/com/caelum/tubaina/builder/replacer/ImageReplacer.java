package br.com.caelum.tubaina.builder.replacer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

import javax.imageio.ImageIO;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.resources.ImageResource;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class ImageReplacer extends PatternReplacer {

	private final List<Resource> resources;

	public ImageReplacer(List<Resource> resources) {
		super("(?s)(?i)\\A\\s*\\[img \\s*(\\S+?)(?:\\s+(.*?)\\s*)?\\]");
		this.resources = resources;

	}

	@Override
	public Chunk createChunk(Matcher matcher) {
		int width = 0;
		String path = matcher.group(1);
		System.out.println(path);
		File image = ResourceLocator.getInstance().getFile(path);
		try {
			BufferedImage measurable = ImageIO.read(image);
			width = measurable.getWidth();
		} catch (IOException e) {
			throw new TubainaException("Image not existant", e);
		} catch (NullPointerException e) {
			throw new TubainaException(path + " is not a valid image");
		}
		resources.add(new ImageResource(image, matcher.group(2)));
		return new ImageChunk(path, matcher.group(2), width);
	}

}
