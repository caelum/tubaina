package br.com.caelum.tubaina.parser.latex;

import com.google.inject.Inject;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.JavaChunk;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class JavaTag implements Tag<JavaChunk> {
	
	private static final String MESSAGE = "[java] Tag is deprecated and can't be used anymore. Use [code java] instead";

	@Inject
	public JavaTag(Indentator indentator) {
		throw new TubainaException(MESSAGE);
	}
	
	@Override
	public String parse(JavaChunk chunk) {
		throw new TubainaException(MESSAGE);
	}

}
