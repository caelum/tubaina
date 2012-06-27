	package br.com.caelum.tubaina.resources;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.parser.html.desktop.ImageTag;
import br.com.caelum.tubaina.util.Utilities;

public class HtmlResourceManipulator implements ResourceManipulator {

	private File imageDestinationPath;

	private static final Logger LOG = Logger.getLogger(HtmlResourceManipulator.class);

	private static final int PAGE_WIDTH = 684;

	private final Map<String, Integer> indexes;

	private final File logo;

	public HtmlResourceManipulator(File imageDestinationPath, Map<String,Integer> indexes, File logo) {
		this.imageDestinationPath = imageDestinationPath;
		this.indexes = indexes;
		this.logo = logo;
	}
	
	public void copyAnswer(AnswerChunk chunk) {
		//	In HTML, the answer is kept below the question, not in a extra book. Therefore, there's no
		// need to copy them.
	}

	public void copyAndScaleImage(File srcImage, String attribs) {
		Double scale = new Double(new ImageTag().getScale(attribs));
		boolean shouldResize = new ImageTag().shouldResize(attribs);
		boolean tooBig = false;
		
		if (srcImage.exists()) {
			File destinationFile = new File(this.imageDestinationPath, FilenameUtils.getName(srcImage.getPath()));
			if (!destinationFile.exists()) {
				
				try {
					FileUtils.copyFileToDirectory(srcImage, this.imageDestinationPath);
					LOG.info("copying image: "+ srcImage.getPath());
					ImageOutputStream stream = new FileImageOutputStream(destinationFile);
					
					if (Utilities.getImageWidth(srcImage) > PAGE_WIDTH) {
					    LOG.warn("Image: '" + srcImage.getPath() + "' is too big for the page");
						scale = 100.0;
						tooBig = true;
					}
					if (shouldResize || tooBig) {
					    Utilities.resizeImage(srcImage, stream, Utilities.getFormatName(srcImage), PAGE_WIDTH, (scale)/100.0);
					}
					srcImage = destinationFile;
		
					Utilities.getImageWithLogo(srcImage, stream, Utilities.getFormatName(srcImage), logo);
					stream.close();
				} catch (IOException e) {
					LOG.warn("Error while copying " + srcImage.getPath() + ":\n" +
							"\t\t" + e.getMessage());
					throw new TubainaException("Couldn't copy image", e);
				}
			} else {
				LOG.warn("Error while copying '" + srcImage.getPath() + "':\n" +
						"\t\tDestination image '" + destinationFile.getPath() + "' already exists");
			}
		} else {
			LOG.warn("Image: '" + srcImage.getPath() + "' doesn't exist");
			throw new TubainaException("Image Doesn't Exists");
		}
		
	}

	public void copyIndex(String name, int dirNumber) {
		indexes.put(name, dirNumber);
	}

	public void copyExercise(int id) {
		// nothing to do
		
	}

}
