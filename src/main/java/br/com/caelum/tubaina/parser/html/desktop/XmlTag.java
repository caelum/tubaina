package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.XmlChunk;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

@Deprecated
public class XmlTag implements Tag<XmlChunk> {

	private static final String MESSAGE = "[xml] Tag is deprecated and can't be used anymore. Use [code xml] instead";

	public XmlTag(Indentator indentator) {
		throw new TubainaException(MESSAGE);
	}
	
	@Override
	public String parse(XmlChunk chunk) {
		throw new TubainaException(MESSAGE);
	}

}
