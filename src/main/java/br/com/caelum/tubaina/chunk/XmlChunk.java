package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class XmlChunk extends AbstractChunk<XmlChunk> {

	private String content;
	private final String options;

	public XmlChunk(String options, String content) {
		this.options = options;
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public String getOptions() {
		return options;
	}
}
