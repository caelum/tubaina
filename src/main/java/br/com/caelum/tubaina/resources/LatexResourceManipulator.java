package br.com.caelum.tubaina.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.parser.latex.ImageTag;
import br.com.caelum.tubaina.util.Utilities;

public class LatexResourceManipulator implements ResourceManipulator {

	private File answerFile;

	private File imagePath;

	private static final Logger LOG = Logger.getLogger(LatexResourceManipulator.class);

	private static final int PAGE_WIDTH = TubainaBuilder.getMaximumWidth();

	private final boolean noAnswer;

	public LatexResourceManipulator(File imagePath, File answerFile, boolean noAnswer) {
		this.imagePath = imagePath;
		this.answerFile = answerFile;
		this.noAnswer = noAnswer;
	}

	@Override
	public void copyAndScaleImage(File srcImage, String scale) {
		Integer width = new ImageTag().getScale(scale);
		if (srcImage.exists()) {
			File destinationPath = new File(this.imagePath, FilenameUtils.getName(srcImage.getPath()));
			if (!destinationPath.exists()) {
				try {
					if (width == null && Utilities.getImageWidth(srcImage) > PAGE_WIDTH) {
						LOG.warn("Image: '" + srcImage.getPath() + "' is too big for the page");
					}
					FileUtils.copyFileToDirectory(srcImage, this.imagePath);
				} catch (IOException e) {
					LOG.warn("Error while copying " + srcImage.getPath() + ":\n" + "\t\t" + e.getMessage());
					throw new TubainaException("Couldn't copy image", e);
				}
			} else {
				LOG.warn("Error while copying '" + srcImage.getPath() + "':\n" + "\t\tDestination image '"
						+ destinationPath.getPath() + "' already exists");
			}
		} else {
			LOG.warn("Image: '" + srcImage.getPath() + "' doesn't exists");
			throw new TubainaException("Image doesn't exists");
		}

	}

	@Override
	public void copyAnswer(AnswerChunk chunk) {
		String answer = "\\begin{itemize}\n" + 
				"\\item[\\ref{ans:" + chunk.getId() + "}.]{" + 
				chunk.getContent() + "}\n" +
				"\\end{itemize}\n";

		try {
			PrintStream stream = new PrintStream(new FileOutputStream(this.answerFile, true), true, "UTF-8");
			if (noAnswer)
				stream.append("");
			else
				stream.append(answer);
			stream.close();
		} catch (FileNotFoundException e) {
			throw new TubainaException("couldn't copy answer", e);
		} catch (UnsupportedEncodingException e) {
			throw new TubainaException("Invalid Encoding", e);
		}
	}
	@Override
	public void copyExercise(int id) {
		String answer = "\\answerHead{\\ref{ex:"+id+"}}\n"; 
		try {
			PrintStream stream = new PrintStream(new FileOutputStream(this.answerFile, true), true, "UTF-8");
			if (noAnswer)
				stream.append("");
			else
				stream.append(answer);
			stream.close();
		} catch (FileNotFoundException e) {
			throw new TubainaException("couldn't copy answer", e);
		} catch (UnsupportedEncodingException e) {
			throw new TubainaException("Invalid Encoding", e);
		}		
	}
	@Override
	public void copyIndex(String name, int dirNumber) {
		// Latex makes index automaticaly
	}

}
