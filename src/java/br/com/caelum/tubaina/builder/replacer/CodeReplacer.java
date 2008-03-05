package br.com.caelum.tubaina.builder.replacer;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.Tubaina;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.util.Utilities;

public class CodeReplacer extends AbstractReplacer {

	public CodeReplacer() {
		super("code");
	}

	@Override
	public Chunk createChunk(String options, String content) {
		int maxLineLenght = Utilities.maxLineLength(content) - Utilities.getMinIndent(content); 
		if(maxLineLenght >Tubaina.MAX_LINE_LENGTH)
			throw new TubainaException ("Chapter " + Chapter.getChaptersCount() + 
										"  -  Code has " + maxLineLenght + " columns:\n\n" + content);
		return new CodeChunk(content, options);
	}

}
