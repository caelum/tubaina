package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class RubyTag implements Tag {

	private static final String MESSAGE = "[ruby] Tag is deprecated and can't be used anymore. Use [code ruby] instead";

	public String parse(Chunk chunk) {
		throw new TubainaException(MESSAGE);
	}

}