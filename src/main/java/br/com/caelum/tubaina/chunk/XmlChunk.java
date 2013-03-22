package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Parser;

public class XmlChunk implements Chunk {
	private String content;
	private final String options;

	public XmlChunk(String options, String content) {
		this.options = options;
		this.content = content;
	}

	public String asString(Parser p) {
		return p.parseXml(content, options);
	}
}
