package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.JavaChunk;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class JavaTag implements Tag<JavaChunk> {

	private static final String MESSAGE = "[java] Tag is deprecated and can't be used anymore. Use [code java] instead";

	@Override
	public String parse(JavaChunk chunk) {
		throw new TubainaException(MESSAGE);
	}


}
