package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.XmlChunk;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class XmlTag implements Tag<XmlChunk> {

	private static final String MESSAGE = "[xml] Tag is deprecated and can't be used anymore. Use [code xml] instead";

	@Override
	public String parse(XmlChunk chunk) {
		throw new TubainaException(MESSAGE);
	}

}
