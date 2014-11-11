package br.com.caelum.tubaina.parser.markdown;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Tag;

public class ImageTag implements Tag<ImageChunk> {

	@Override
	public String parse(ImageChunk chunk) {
		String imgsrc = FilenameUtils.getName(chunk.getPath());
		return "<img src=\"" + imgsrc + "\" />";
	}
}
