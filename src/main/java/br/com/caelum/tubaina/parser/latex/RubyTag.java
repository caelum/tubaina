package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class RubyTag implements Tag {

	private static final String MESSAGE = "[ruby] Tag is deprecated and can't be used anymore. Use [code ruby] instead";

	public RubyTag(Indentator indentator) {
		throw new TubainaException(MESSAGE);
	}
	
	public String parse(String string, String opts) {
		throw new TubainaException(MESSAGE);
	}
}
