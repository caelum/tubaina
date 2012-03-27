package br.com.caelum.tubaina.builder.replacer;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaBuilder;
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
		if(maxLineLenght >TubainaBuilder.getCodeLength())
			throw new TubainaException ("Chapter " + Chapter.getChaptersCount() + 
										"  -  Code has " + maxLineLenght + " columns out of " + TubainaBuilder.getCodeLength() + ":\n\n" + content);
		return new CodeChunk(content, options);
	}

}
