package br.com.caelum.tubaina.builder.replacer;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.resources.ImageResource;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceLocator;

import com.lowagie.text.Image;

public class ImageReplacer extends PatternReplacer {

	private final List<Resource> resources;

	public ImageReplacer(List<Resource> resources) {
		super("(?s)(?i)\\A\\s*\\[img \\s*(\\S+?)(?:\\s+(.*?)\\s*)?(?:\".*\")?\\]");
		this.resources = resources;

	}

	@Override
	public Chunk createChunk(Matcher matcher) {
		double width = 0;
		String path = matcher.group(1);
		File imageFile = ResourceLocator.getInstance().getFile(path);

		Image image = ResourceLocator.getInstance().getImage(path);
		int dpi = image.getDpiX();
		if(dpi == 0)
			dpi = 72;
		width = image.getPlainWidth();
		
		resources.add(new ImageResource(imageFile, matcher.group(2)));
		return new ImageChunk(path, matcher.group(2), width, dpi);
	}

}
