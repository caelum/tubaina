package br.com.caelum.tubaina.parser.markdown;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Tag;

import com.google.inject.Inject;

public class ImageTag implements Tag<ImageChunk> {

	private S3Path path;
	
	@Inject
	public ImageTag(S3Path path) {
		this.path = path;

	}
	@Override
	public String parse(ImageChunk chunk) {
		String imgsrc = FilenameUtils.getName(chunk.getPath());
		return "\n\n<img src=\"" + path.getRemotePath() + imgsrc + "\" />\n\n";
	}
}
