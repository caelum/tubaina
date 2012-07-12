package br.com.caelum.tubaina.builder.replacer;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.util.Utilities;

public class CodeReplacer extends AbstractReplacer {

	public CodeReplacer() {
		super("code");
	}

	@Override
	public Chunk createChunk(String options, String content) {
	    //TODO: use the real tab size, it may be different depending of which parseType is being used
	    SimpleIndentator indentator = new SimpleIndentator(4);
		int maxLineLength = Utilities.maxLineLength(indentator.indent(content)) - Utilities.getMinIndent(indentator.indent(content)); 
		if (maxLineLength > TubainaBuilder.getCodeLength())
			throw new TubainaException ("Chapter " + ChapterBuilder.getChaptersCount() + 
										"  -  Code has " + maxLineLength + " columns out of " + TubainaBuilder.getCodeLength() + ":\n\n" + content);
		return new CodeChunk(content, options);
	}

}
