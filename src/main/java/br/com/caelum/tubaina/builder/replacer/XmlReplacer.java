package br.com.caelum.tubaina.builder.replacer;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.XmlChunk;
import br.com.caelum.tubaina.util.Utilities;

public class XmlReplacer extends AbstractReplacer {

	public XmlReplacer() {
		super("xml");
	}

	@Override
	public Chunk createChunk(String options, String content) {
		int maxLineLenght = Utilities.maxLineLength(content) - Utilities.getMinIndent(content); 
		if(maxLineLenght >TubainaBuilder.getCodeLength())
			throw new TubainaException ("Chapter " + Chapter.getChaptersCount() + 
					"  -  Code has " + maxLineLenght + " columns out of " + TubainaBuilder.getCodeLength() + ":\n\n" + content);	
		return new XmlChunk(options, content);
	}

}
