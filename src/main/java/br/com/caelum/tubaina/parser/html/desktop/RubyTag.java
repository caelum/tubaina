package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.RubyChunk;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class RubyTag implements Tag<RubyChunk> {

	private static final String MESSAGE = "[ruby] Tag is deprecated and can't be used anymore. Use [code ruby] instead";

	public RubyTag(Indentator indentator) {
		throw new TubainaException(MESSAGE);
	}
	
	@Override
	public String parse(RubyChunk chunk) {
		throw new TubainaException(MESSAGE);
	}
}