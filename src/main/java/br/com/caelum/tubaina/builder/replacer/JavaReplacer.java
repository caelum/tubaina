package br.com.caelum.tubaina.builder.replacer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.chunk.JavaChunk;
import br.com.caelum.tubaina.resources.ResourceLocator;
import br.com.caelum.tubaina.util.Utilities;

public class JavaReplacer extends AbstractReplacer {

	private static final Logger LOG = Logger.getLogger(JavaReplacer.class);
	
	public JavaReplacer() {
		super("java");
	}

	@Override
	public Chunk createChunk(String options, String content) {
		//if java code is from an external file
		if (options.contains("src")) {
			File file = ResourceLocator.getInstance().getFile(content.trim());
			try {
				content = FileUtils.readFileToString(file, "UTF-8");
			} catch (IOException e) {
				LOG.warn("Failed to read file", e);
			}
		}
		int maxLineLength = Utilities.maxLineLength(content) - Utilities.getMinIndent(content); 
		if(maxLineLength >TubainaBuilder.getCodeLength())
			throw new TubainaException ("Chapter " + ChapterBuilder.getChaptersCount() + 
					"  -  Code has " + maxLineLength + " columns out of " + TubainaBuilder.getCodeLength() + ":\n\n" + content);
		return new JavaChunk(options, content);
	}

}
