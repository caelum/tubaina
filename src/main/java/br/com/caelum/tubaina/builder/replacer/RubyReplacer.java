package br.com.caelum.tubaina.builder.replacer;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.RubyChunk;
import br.com.caelum.tubaina.util.Utilities;

public class RubyReplacer extends AbstractReplacer {

	public RubyReplacer() {
		super("ruby");
	}

	@Override
	public Chunk createChunk(String options, String content) {
		int maxLineLength = Utilities.maxLineLength(content) - Utilities.getMinIndent(content); 
		if(maxLineLength >TubainaBuilder.getCodeLength())
			throw new TubainaException ("Chapter " + Chapter.getChaptersCount() + 
					"  -  Code has " + maxLineLength + " columns out of " + TubainaBuilder.getCodeLength() + ":\n\n" + content);
		return new RubyChunk(content, options);
	}

}
