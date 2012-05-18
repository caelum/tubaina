package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class JavaTag implements Tag {

	private static final String MESSAGE = "[java] Tag is deprecated and can't be used anymore. Use [code java] instead";

	public String parse(String string, String opts) {
		throw new TubainaException(MESSAGE);
	}


}
