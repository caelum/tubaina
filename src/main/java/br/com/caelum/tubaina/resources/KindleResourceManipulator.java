package br.com.caelum.tubaina.resources;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.AnswerChunk;

public class KindleResourceManipulator implements ResourceManipulator {

    private File imageDestinationPath;

    private static final Logger LOG = Logger.getLogger(KindleResourceManipulator.class);

    private final Map<String, Integer> indexes;

    public KindleResourceManipulator(File imageDestinationPath, Map<String, Integer> indexes) {
        this.imageDestinationPath = imageDestinationPath;
        this.indexes = indexes;
    }

    public void copyAnswer(AnswerChunk chunk) {
        // In HTML, the answer is kept below the question, not in a extra book.
        // Therefore, there's no
        // need to copy them.
    }

    public void copyAndScaleImage(File srcImage, String attribs) {
        if (srcImage.exists()) {
            File destinationFile = new File(this.imageDestinationPath, FilenameUtils
                    .getName(srcImage.getPath()));
            if (!destinationFile.exists()) {

                try {
                    FileUtils.copyFileToDirectory(srcImage, this.imageDestinationPath);
                    LOG.debug("copying image: " + srcImage.getPath());

                } catch (IOException e) {
                    LOG.warn("Error while copying " + srcImage.getPath() + ":\n" + "\t\t"
                            + e.getMessage());
                    throw new TubainaException("Couldn't copy image", e);
                }
            } else {
                LOG.warn("Error while copying '" + srcImage.getPath() + "':\n"
                        + "\t\tDestination image '" + destinationFile.getPath()
                        + "' already exists");
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
